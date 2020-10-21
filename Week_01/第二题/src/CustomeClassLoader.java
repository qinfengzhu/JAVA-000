import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//第二题
public class CustomeClassLoader  extends ClassLoader {

	public static void main(String[] args) throws IOException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {	
		try {
			  Class<?> hello = new CustomeClassLoader().findClass("Hello");
	           Method method = hello.getMethod("hello");
	           method.setAccessible(true);
	           method.invoke(hello.newInstance());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
   
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException{		
		try
		{
			String xlassFilePath = System.getProperty("user.dir")+File.separator+"resources"+File.separator+"Hello.xlass";
			File xlassFile = new File(xlassFilePath);
			FileInputStream fs = new FileInputStream(xlassFile);
		    byte[] buffer = new byte[(int) xlassFile.length()];
		    int offset = 0;
		    int numRead = 0;
		    while (offset < buffer.length && (numRead = fs.read(buffer, offset, buffer.length - offset)) >= 0) {
		            offset += numRead;
		    }
		    fs.close();
		    //做异或操作也就等于225-x
		    for(int i=0;i<buffer.length;i++) {
		    		buffer[i]=(byte) (buffer[i]^0XFF);
		    }
			return defineClass(name, buffer, 0,buffer.length);
		}
		catch(Exception ex)
		{
			System.out.print("加载类字节文件出现异常了。。。。。。");
			throw new ClassNotFoundException(name+" class not found");
		}
	}
}
