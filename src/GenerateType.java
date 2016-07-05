/**
 * Created by fuzhipeng on 16/7/5.
 */
public enum GenerateType {
    LazyUnSafe,LazySafe,Hungry,DoubleCheck,StaticInner,Enum;

    public byte[] getBinaryContent(String packageString,String name) {
        String result = null;
        switch (this) {
            case LazyUnSafe:
                result=lazyUnSafe(packageString,name);
               break;

            case LazySafe:
                result=lazySafe(packageString,name);
               break;

            case StaticInner:
                result=staticInner(packageString,name);
               break;

            case Hungry:
                result=hungry(packageString,name);
               break;

            case DoubleCheck:
                result=doubleCheck(packageString,name);
               break;

            case Enum:
                result=enumString(packageString,name);
               break;

            default:
                break;
        }
        if(result==null||result.isEmpty())
            return null;
        return result.getBytes();
    };
    private String getPackageJoint(String packageName){
        if(packageName==null||packageName.trim().length()==0)
            return "";
        else
            return "package " +packageName+";\n" + "\n" ;
    };

    private String lazyUnSafe(String packageName,String className) {
            return getPackageJoint(packageName)+
                    "public class "+className+" {\n" +
                    "    private static "+className+" instance;\n" +
                    "\n" +
                    "    private "+className+"() {\n" +
                    "    }\n" +
                    "\n" +
                    "    public static "+className+" getInstance() {\n" +
                    "        if (instance == null) {\n" +
                    "            instance = new "+className+"();\n" +
                    "        }\n" +
                    "        return instance;\n" +
                    "    }\n" +
                    "}";
    }
    private String lazySafe(String packageName,String className){
        return getPackageJoint(packageName)+
                "public class "+className+" {\n" +
                "    private static "+className+" instance;\n" +
                "\n" +
                "    private "+className+"() {\n" +
                "    }\n" +
                "\n" +
                "    public static synchronized "+className+" getInstance() {\n" +
                "        if (instance == null) {\n" +
                "            instance = new "+className+"();\n" +
                "        }\n" +
                "        return instance;\n" +
                "    }\n" +
                "}";
    }
    private String staticInner(String packageName,String className){
        return getPackageJoint(packageName)+
                "public class "+className+" {\n" +
                "\n" +
                "    private "+className+"() {\n" +
                "    }\n" +
                "\n" +
                "    private static class SingletonInstance {\n" +
                "        private static final "+className+" INSTANCE = new "+className+"();\n" +
                "    }\n" +
                "\n" +
                "    public static "+className+" getInstance() {\n" +
                "        return SingletonInstance.INSTANCE;\n" +
                "    }\n" +
                "}";
    }
    private String hungry(String packageName,String className){
        return getPackageJoint(packageName)+
                "public class "+className+" {\n" +
                "    private static "+className+" instance = new "+className+"();\n" +
                "\n" +
                "    private "+className+"() {\n" +
                "    }\n" +
                "\n" +
                "    public static "+className+" getInstance() {\n" +
                "        return instance;\n" +
                "    }\n" +
                "}";
    }
    private String enumString(String packageName,String className){
        return getPackageJoint(packageName)+
                "public enum "+className+" {\n" +
                "    INSTANCE;\n" +
                "}";
    }
    private String doubleCheck(String packageName,String className){
        return getPackageJoint(packageName)+
                "public class "+className+" {\n" +
                "\n" +
                "    private static volatile "+className+" singleton;\n" +
                "\n" +
                "    private "+className+"() {\n" +
                "    }\n" +
                "\n" +
                "    public static "+className+" getInstance() {\n" +
                "        if (singleton == null) {\n" +
                "            synchronized ("+className+".class) {\n" +
                "                if (singleton == null) {\n" +
                "                    singleton = new "+className+"();\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "        return singleton;\n" +
                "    }\n" +
                "}";
    }

}
