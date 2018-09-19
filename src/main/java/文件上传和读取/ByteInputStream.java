package 文件上传和读取;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @Auther: cpb
 * @Date: 2018/9/19 16:41
 * @Description:
 */
public class ByteInputStream {

    public static void main(String[] args) {
        ByteInputStream byteInputStream = new ByteInputStream();
        byteInputStream.fileOutputStream();
        byteInputStream.fileInputStream();
    }
//    读取文件
    public void fileInputStream(){
        //      创建文件路径
        String dataSourceFile = "F:/test/test.txt";

        File savefile = new File(dataSourceFile);
        try {
            String filetxt = FileUtils.readFileToString( savefile,"utf-8");// 复制临时文件到指定目录下
            System.out.println(filetxt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //    写入文件
    public void fileOutputStream(){
//      创建文件路径
        String dataSourceFile = "F:\\test\\test.txt";
//      创建文件file
        File savefile = new File(dataSourceFile);
        //判断上传文件的保存目录是否存在
        if (!savefile.exists() && !savefile.isDirectory()) {
            System.out.println(dataSourceFile+"目录不存在，需要创建");
            //创建目录
            savefile.mkdir();
        }
//      写入文本信息
        String fileTxt = "你好吗";
        try {
            FileUtils.writeStringToFile(savefile, fileTxt, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
