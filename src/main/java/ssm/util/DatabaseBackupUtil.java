package ssm.util;

import java.io.*;

/**
 * @author chen
 * @create 2019-09-07 23:17
 */
public class DatabaseBackupUtil {

    /**
     * Java代码实现MySQL数据库导出
     *
     * @param hostIP MySQL数据库所在服务器地址IP
     * @param userName 进入数据库所需要的用户名
     * @param password 进入数据库所需要的密码
     * @param savePath 数据库导出文件保存路径
     * @param fileName 数据库导出文件文件名
     * @param databaseName 要导出的数据库名
     * @return 返回true表示导出成功，否则返回false。
     */
    public static boolean exportDatabaseTool(String hostIP, String userName, String password, String savePath, String fileName, String databaseName) throws InterruptedException {
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {// 如果目录不存在
            saveFile.mkdirs();// 创建文件夹
        }
        //file.separator返回文件的分隔符，如果是在UNIX系统上，该值为'/',在microsoft windows系统上，为'\\'
        if(!savePath.endsWith(File.separator)){
            savePath = savePath + File.separator;
        }

        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        try {
            printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(savePath + fileName), "utf8"));
            //mysqldump -uroot -p123456 -P3306 blog > D:\mysql_backup\blog.sql
            Process process = Runtime.getRuntime().exec(" mysqldump -h" + hostIP + " -u" + userName + " -p" + password + " --set-charset=UTF8 " + databaseName);
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while((line = bufferedReader.readLine())!= null){
                printWriter.println(line);
            }
            printWriter.flush();
            if(process.waitFor() == 0){//0 表示线程正常终止。
                return true;
            }
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 导入Mysql数据库
     * @param userName 用户名
     * @param password 密码
     * @param importFilePath 数据库文件路径
     * @param sqlFileName 要导入的文件名
     * @param databaseName 要导入的数据库名
     * @return 返回true表示导出成功，否则返回false。
     * @throws InterruptedException
     */
    public static boolean load( String userName, String password, String importFilePath,
                            String sqlFileName, String databaseName) {
        try {
            //在地址后补充系统默认分隔符
            if (!importFilePath.endsWith(File.separator)) {
                importFilePath = importFilePath + File.separator;
            }
            //数据库备份的sql文件的地址
            String fPath = importFilePath + sqlFileName;//"C:/Users/chenj/junwuyi/mysql_backup/2019/9/20190907-232123.sql";
            Runtime rt = Runtime.getRuntime();

            // 调用 mysql命令行
            //mysql -h127.0.0.1 -uroot -P3306 -p test<C:\chengzheming\backupDatabase\
            Process child = rt.exec("mysql"+" -u" + userName + " -p"+ password +" --default-character-set=utf8 "+ databaseName);
            //控制台的输入信息作为输出流
            OutputStream out = child.getOutputStream();
            String inStr;

            StringBuffer sb = new StringBuffer("");
            String outStr;

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fPath), "utf-8"));

            while ((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }

            outStr = sb.toString();

            OutputStreamWriter writer = new OutputStreamWriter(out, "utf-8");
            //将读取出的输出流写进去
            writer.write(outStr);
            // 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
            writer.flush();

            //关闭输入输出流
            out.close();
            br.close();
            writer.close();
            System.out.println("数据库恢复成功");
            if(child.waitFor() == 0){//0 表示线程正常终止。
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {

            long start = System.currentTimeMillis();
            load("root","123456","C:/Users/chenj/junwuyi/mysql_backup/2019/9/","20190907-232123.sql","blog");
            long end = System.currentTimeMillis();
            System.out.println("共耗时：" + (end - start) + "毫秒");
        }


    }
}
