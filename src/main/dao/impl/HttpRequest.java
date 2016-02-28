package main.dao.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Just to test Api
 * @author Dora
 *
 */
public class HttpRequest {
	
	 
	public static void main(String[] args) {
	        //���� GET ����
		 
		 
/*		 	//1.json��ʽ��Ŀ�����б�һҳ50��������?page=��Ĭ����ʾ��һҳ����
	        String s=HttpRequest.sendGet("http://www.gitmining.net//api/repository", "?page=1");
	        String[] lines = s.split(",");
	        for(String str: lines)
	        	System.out.println(str);*/
	        

		 	/*//2.������Ŀȫ���б�		
	        String s=HttpRequest.sendGet("http://gitmining.net/api/repository/names", "");
	        String[] lines = s.split(",");
	        for(String str: lines)
	        	System.out.println(str);   */
	        
	        //3.������Ŀ����
	        /**
			 * @param owner ��Ŀ�����ߵ�¼�� 	reponame ��Ŀ��
			 * @return json
			 */
        	String s=HttpRequest.sendGet("http://gitmining.net/api/repository/", "resque/resque-loner");
	        String[] lines = s.split(",");
	        for(String str: lines){
	        	     	System.out.println(str);   
	        	
	        }
	    }
	 
	/**
     * ��ָ��URL����GET����������
     * 
     * @param url
     *            ���������URL
     * @param param
     *            ����������������Ӧ���� ?name1=value1&name2=value2 ����ʽ��
     * @return URL ������Զ����Դ����Ӧ���
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
        	
        	//�ϳ�url
            String urlNameString = url + param;
            URL realUrl = new URL(urlNameString);     
            System.out.println(realUrl);
            
            // �򿪺�URL֮�������
            URLConnection connection = realUrl.openConnection();
            
            // ��ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            
            
            
        } catch (Exception e) {
            System.out.println("GET failed��" + e);
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

	 
}


