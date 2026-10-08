package stancempire.lavender.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.io.*;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class LavenderApi
{
    public static void main(String[] args)
    {

        Logger logger = LogUtils.getLogger();
        // TODO 2026-10-05: Find a better way to do this
        // Get game directory pre-launch
        Path gameDirectory = null;
        for(int i = 0; i < args.length; i++)
        {
            String arg = args[i];
            if(arg.equals("--gameDir"))
            {
                gameDirectory = Path.of(args[i + 1]);
            }
        }
        if(gameDirectory == null)
        {
            throw new RuntimeException("Unable to locate gameDirectory on start-up args!");
        }

        // (1) Create custom using class path so that we
        // modify the classpath and substitute the system
        // class loader
        // (1.1) Get existing classpath
        String classPathProperty = System.getProperty("java.class.path");
        String[] classPaths = classPathProperty.split(File.pathSeparator);
        // (1.2) Convert string to URIs
        ArrayList<URL> classPathUrlList = new ArrayList<>();
        String vanillaPatchedPath = null;
        for(String path : classPaths)
        {
            if(path.contains("vanillaPatched") || path.contains("binApi"))
            {
                if(vanillaPatchedPath == null)
                {
                    vanillaPatchedPath = path;
                }
                continue;
            }
            URL pathUrl;
            try
            {
                Path pathObject = Path.of(path);
                pathUrl = pathObject.toUri().toURL();
            }
            catch(MalformedURLException e)
            {
                throw new RuntimeException();
            }
            classPathUrlList.add(pathUrl);
        }
        if(vanillaPatchedPath == null)
        {
            throw new RuntimeException("Could not find patched vanilla jar!");
        }
        // (2) Load Mod Jars into classpath and perform any relevant startup
        // (2.1) Search for mod jars
        File modDirectory = gameDirectory.resolve("mods").toFile();
        if(!modDirectory.exists())
        {
            boolean modDirectoriesCreated = modDirectory.mkdirs();
        }
        File[] modFiles = modDirectory.listFiles();
        if(modFiles == null)
        {
           modFiles = new File[0];
        }
        for(File modFile : modFiles)
        {
           // (2.1.1) For mod each candidate, get the modInfo.json
           // file. Skip if it doesn't exist (not valid mod).
           try(JarFile modJar = new JarFile(modFile))
           {
               ZipEntry modInfo = modJar.getEntry("modInfo.json");
               if(modInfo == null)
               {
                   // Not a valid mod
                   continue;
               }
               InputStream modInfoInputStream = modJar.getInputStream(modInfo);
               Gson gson = new Gson();
               JsonReader jsonReader = new JsonReader(new InputStreamReader(modInfoInputStream));
               JsonObject modInfoJson = gson.fromJson(jsonReader, JsonObject.class);
               // (2.1.2) validate JSON
               // (2.1.3) Add mod to class path
               classPathUrlList.add(modFile.toURI().toURL());
           }
           catch(IOException e)
           {
               // TODO 2026-10-05: Add better exception handling here
               throw new RuntimeException(e);
           }
        }

        // (3) Start game
        // (3.1) Prepend patched vanilla jar so this is always
        // first in the classpath order
        try
        {
            Path vanillaPatchedPathObj = Path.of(vanillaPatchedPath);
            classPathUrlList.addFirst(vanillaPatchedPathObj.toUri().toURL());
        }
        catch(MalformedURLException e)
        {
            // TODO 2026-10-05: Add better exception handling here
            throw new RuntimeException(e);
        }
        // (3.2) Finalise classpath and create custom classloader
        URL[] classPathUrls = new URL[classPathUrlList.size()];
        classPathUrlList.toArray(classPathUrls);
        ClassLoader platformClassLoader = ClassLoader.getPlatformClassLoader();
        // Set platform class loader as parent to mimic the behaviour
        // of the system class loader
        try(URLClassLoader lavenderClassLoader = new URLClassLoader(classPathUrls, platformClassLoader))
        {
            // (3.3) Get Vanilla main class
            // TODO 2026-10-03: Move this entrypoint class reference to a constant or config
            // TODO 2026-10-03: Handle the case of server main as well
            Class<?> minecraftMain = lavenderClassLoader.loadClass("net.minecraft.client.main.Main");
            Method minecraftMainMethod = minecraftMain.getMethod("main", String[].class);
            // (3.4) Run Minecraft
            try
            {
                minecraftMainMethod.invoke(null, (Object)args);
            }
            catch(Exception e)
            {
                logger.error(e.getMessage());
                throw new RuntimeException("An unhandled error occurred during Minecraft Runtime!");
            }
        }
        catch(IOException e)
        {
            logger.error("An error occurred in the Lavender class loader, aborting!");
            return;
        }
        catch(ClassNotFoundException | NoSuchMethodException e)
        {
            logger.error("Could not find Minecraft main class or method, aborting!");
            return;
        }
    }
}
