package com.common.commonUtil;

import jxl.Workbook;
import jxl.format.*;
import jxl.write.*;
import jxl.write.Colour;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;


public class Excel {


	/**
	 * 统一导出excel
	 * @param filename excel文件名字
	 * @param titleJa  表头二维数组
	 * @param fieldJa  正文内容对应的key的字段
	 * @param listContent  正文内容
	 * @param titleLength 表头占了几行
	 * @param
	 * @return
	 */
	public static String CommonExcel(String filename, String[][] titleJa, String[] fieldJa, List<Object> listContent, Integer titleLength, OutputStream output ){
		String result = "恭喜，excel导出成功";
//		OutputStream output = null;
//		response.setCharacterEncoding("UTF-8");// 设置字符集

		Integer rowspan = 0;
		Integer colspan = 0;
		Integer mergeColspanPre = 0;//列指针，  合并单元格后，列指针指向第几列
		Integer secondColTmp = 0;//保存excel第二行标题的起始列值
		try {

			/*response.setHeader("Content-disposition", filename);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="+filename);
			response.setHeader("Pragma", "No-cache");
			 output = response.getOutputStream();*/
			//创建工作簿
			WritableWorkbook workbook = Workbook.createWorkbook(output);
			//创建工作表
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			//设置单元格宽度
			sheet.getSettings().setDefaultColumnWidth(20); //设置列的默认宽度
			/*for(int i=1;i<=titleJa.length;i++){  //i=0，留给序号，不需要这么大宽度
				sheet.setColumnView(i, 20);
			}*/

    		/*sheet.setColumnView(2, 20);
    		sheet.setColumnView(3, 25);
    		sheet.setColumnView(4, 25);*///设置单元格宽度
			//sheet.autoSizeColumn(1, true);//自适应单元格宽度，此版本太低，不支持
			//设置单元格字体
			WritableFont normalFont =  new WritableFont(WritableFont.ARIAL,10);
			WritableFont boldFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);
			//标题居中
			WritableCellFormat wcf_center = new WritableCellFormat(boldFont);
			wcf_center.setAlignment(jxl.format.Alignment.CENTRE);//水平居中
			wcf_center.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//垂直居中
			wcf_center.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); //单元格边框
			workbook.setColourRGB(jxl.format.Colour.BLUE,91,155, 213);
			wcf_center.setBackground(jxl.format.Colour.BLUE);  //单元格背景颜色

			//正文居中
			WritableCellFormat wcf_left = new WritableCellFormat(normalFont);

			//excel列标题
			for (int i = 0; i < titleJa.length; i++) {

				String titleString = titleJa[i][0];
				colspan = Integer.parseInt(titleJa[i][1]);
				rowspan = Integer.parseInt(titleJa[i][2]);
				Integer line = Integer.parseInt(titleJa[i][3]);

				if (1==(line)) {
					//合并第一行的所有单元格，例如当i=0时候，表示当轮到第1个标题时候
					//开始将它第1列的第1行合并到第（所占几列-1）列的第（所占几行-1）行
					sheet.mergeCells(mergeColspanPre,0,mergeColspanPre+colspan-1,rowspan-1);

					//给单元格赋值，（第几列，第几行，内容，样式）
					sheet.addCell(new Label(mergeColspanPre, line-1, titleString, wcf_center));
					//保存列指针
					mergeColspanPre = mergeColspanPre+colspan;

					//当line一等于2的时候，就是表示开始保存第二行excel标题了，
					//所以这里保存最后一次i的值   就是第二行excel标题的起始列值；
					if (colspan==1) {
						secondColTmp = i;
					}

				}else{

					sheet.addCell(new Label(secondColTmp+1, line-1, titleString, wcf_center));
					secondColTmp++;
				}

			}

			//excle正文数据
			for (int i = 0; i < listContent.size(); i++) {
				Map<String,Object> object = (Map) listContent.get(i);

				for (int j = 0; j < fieldJa.length; j++) {
					sheet.addCell(new Label(j, i+titleLength, String.valueOf(object.get(fieldJa[j])), wcf_left));
				}


				//遍历结果
				/*Iterator<Map.Entry<String, String>> it = object.entrySet().iterator();
				int j=0;
				while (it.hasNext()) {
					Map.Entry<String, String> entry = it.next();
					System.out.println("key----"+entry.getKey()+"---value---"+entry.getValue());
					j++;
				}*/
//				for (Map.Entry<String, Object> entry : object.entrySet()){
//					System.out.println("key--->"+entry.getKey()+"-----value--->"+String.valueOf(entry.getValue()));
//				}
			}


			//将缓存的内容写入到excel中

			workbook.write();
//			output.flush();
			workbook.close();

		} catch (Exception e) {
			result="excel导出失败，原因："+e.getMessage();
			System.out.println(result);
			e.printStackTrace();

		}finally{
			if (output!=null){
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;

	}





	 /*public static String getZhyybbExcel(String filename,JSONArray titleJa,List<Object> listContent,OutputStream os,int num){
	    	String result = "恭喜，excel导出成功";
	    	Integer rowspan = 0;
	    	Integer colspan = 0;

	    	try {
				//创建工作簿
	    		WritableWorkbook workbook = Workbook.createWorkbook(os);
	    		//创建工作表
	    		WritableSheet sheet = workbook.createSheet("Sheet1", 0);
	    		sheet.setColumnView(2, 20);
	    		sheet.setColumnView(3, 25);
	    		sheet.setColumnView(4, 25);
	    		//设置单元格字体
	    		WritableFont normalFont =  new WritableFont(WritableFont.ARIAL,10);
	    		WritableFont boldFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);
	    		//标题居中
	    		WritableCellFormat wcf_center = new WritableCellFormat(boldFont);
	    		wcf_center.setAlignment(jxl.format.Alignment.CENTRE);

	    		//正文居中
	    		WritableCellFormat wcf_left = new WritableCellFormat(normalFont);
	    		//excel第一行列标题
	    		for (int i = 0; i < titleJa.size(); i++) {
	    			JSONArray list = JSONArray.fromObject(titleJa.get(i));
	    			for (int j = 0; j < list.size(); j++) {
	    				JSONObject jo = JSONObject.fromObject(list.get(j));
	    				 Integer rowspanTmp =Integer.parseInt(jo.optString("rowspan"));
	    				 if (!"".equals(rowspanTmp)||null!=rowspanTmp) {
							rowspan = rowspanTmp;
						}
	    				 Integer colspanTmp = Integer.parseInt(jo.optString("colspan"));
	    				 if (!"".equals(colspanTmp)||null!=colspanTmp) {
	    					 colspan = colspanTmp;
							}
	    				 if (!"".equals(rowspanTmp)||!"".equals(colspanTmp)) {
	    					//合并第j列第一行到第j+colspan列第rowspan行的所有单元格
		    				 sheet.mergeCells(j,0,j+colspan,rowspan);
						}

	    				String titleString = jo.optString("title");
	    				sheet.addCell(new Label(j, i, titleString, wcf_center));
					}

				}

	    		//excle正文数据
	    		for (int i = 0; i < listContent.size(); i++) {
	    			System.out.println("listcontent------"+listContent.get(i));
					JSONObject object=JSONObject.fromObject(listContent.get(i));
					Iterator it=object.keys();
					int j=0;
					while (it.hasNext()) {
						String key=String.valueOf(it.next());
						if("xh".equals(key)){
							sheet.addCell(new Label(0, i+1, object.getString(key), wcf_left));
						}else {

							sheet.addCell(new Label(j+1, i+1, object.getString(key), wcf_left));
						}
						sheet.addCell(new Label(j, rowspan+1, object.getString(key), wcf_left));
						System.out.println("key----"+key+"---value---"+object.getString(key));
						j++;
					}
				}
	    		if(num>0){
	    			Field[] fields=null;
	    			int i=1;
	    			for(Object obj:listContent){
	    				fields=obj.getClass().getDeclaredFields();
	    				int j=0;
	    				for(Field v:fields){
	    					v.setAccessible(true);
	    					Object va=v.get(obj);
	    					if (va==null) {
	    						va="";
	    					}
	    					sheet.addCell(new Label(j, i, va.toString(), wcf_left));
	    					j++;
	    				}
	    				i++;
	    			}

	    		}
	    		//将缓存的内容写入到excel中
	    		workbook.write();
	    		workbook.close();

			} catch (Exception e) {
				result="excel导出失败，原因："+e.toString();
				System.out.println(result);
				e.printStackTrace();
				
			}finally{
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			}
			return result;
	    	
	    }*/

}
