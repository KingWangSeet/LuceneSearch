package cn.edu.nwsuaf.Action;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;



import cn.edu.nwsuaf.Model.DisplayData;
import cn.edu.nwsuaf.Model.SearchMode;
import cn.edu.nwsuaf.Service.Impl.IndexService;
import cn.edu.nwsuaf.Service.Impl.ProteinService;
import cn.edu.nwsuaf.Service.Impl.ProteinindexService;
import cn.edu.nwsuaf.bean.Pindex;
import cn.edu.nwsuaf.bean.Protein;
import cn.edu.nwsuaf.exception.ServiceException;

public class IndexAction {
	private static String dataPath=ServletActionContext.getServletContext().getRealPath("/")+"\\Lucene Text";
	
	private ProteinindexService proteinindexService;
	private IndexService indexService;
	private ProteinService proteinService;
	
	private String keyWords;// 存入搜索的关键字信息
	private SearchMode searchMode = new SearchMode();
	
	
	// 前台获取属性
	private int page = 1;
	private int rows = 10;
	private int totalRows;
	private int totalPage;	
	
	//索引Id
	private int id;
	private  Pindex index;//修改的Index
	
	//前台展示
	private List<Pindex>indexList;  //索引信息列表
	private List<String> findURLList;//URL信息列表
	private List<DisplayData> dispaleList;//用户检索信息展示列表
    private List<Protein>proteinList;
	
    /** 索引页面缓冲 */    
    private int maxBufferedDocs = 500;     
	
    private long costSearchTime;
    
    /***
	 * 管理人员 初始化查看索引文件信息
	 * @return
	 */
	public String viewIndex(){
		try {
			page = 1;
			rows = 10;
			setIndexList(indexService.findIndexListFirst(page, rows));
			totalRows =  indexService.countFirst();
			if (totalRows % rows == 0) {
				totalPage = totalRows / rows;
			} else {
				totalPage = totalRows == 0 ? 1 : (totalRows / rows + 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	/***
	 * 管理人员条件查找索引信息
	 */
	public String findIndex() {
		try {
			searchMode.setKeyWords(java.net.URLDecoder.decode(searchMode.getKeyWords(), "UTF-8"));
			indexList=indexService.findIndexList(searchMode,page, rows);
			totalRows=indexService.countFindIndex(searchMode);
			if (totalRows % rows == 0) {
				totalPage = totalRows / rows;
			} else {
				totalPage = totalRows == 0 ? 1 : (totalRows / rows + 1);
			}
			//System.out.println("page"+page);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	/***
	 * 初始化索引修改页面
	 * @return
	 */
	
	public String initIndexEdit() {
		try {
			if (id == 0) {
				index = null;
			} else {				
				index = indexService.findById(Pindex.class, id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	/***
	 * //修改or添加
	 * @return
	 */
	
	public String modifiIndex(){
		try {
			//System.out.println("idnex:"+id);
			List<Pindex> s=new ArrayList<Pindex>();
			s=indexService.findByHQL("from Index where id="+id);
			if(s.size()==0){
				indexService.save(index);
			}else{
				//System.out.println("indexName:"+index.getAuthor());
				indexService.update(index);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
		return "success";
	}
	/***
	 * 删除索引
	 * @return
	 */
	
	public String deleteIndex() {
		try {
			 index = indexService.findById(Pindex.class, id);
			indexService.delete(index);
		} catch (ServiceException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	
	/***
	 *管理人员 创建索引信息
	 * @return
	 */
	public String createIndex(){
		
		try {
			proteinList=indexService.createIndex(dataPath);
			proteinService.batchUpdateResult(Protein.class, proteinList);
			return "success";
		} catch (Exception e) {
			return "error";
		}
	}
	
	
	/***
	 * 前台用户进行蛋白质实体信息的检索
	 * @return
	 */
	public String Search() {
		try {
			//proteinindexService.saveProteinToTxt();
			//proteinindexService.transFormTxtToPDF(dataPath,searchMode.getKeyWords());
			long startTime = new Date().getTime();
			searchMode.setKeyWords(java.net.URLDecoder.decode(searchMode.getKeyWords(), "UTF-8"));
			//findURLList=proteinindexService.searchByIndex(searchMode, currentPage, pageSize);
			dispaleList=indexService.searchByIndex(searchMode, page, rows);
			totalRows=indexService.searchByIndexCount(searchMode,page, rows);
			 long endTime = new Date().getTime();
			 costSearchTime=endTime-startTime;
			if (totalRows % rows == 0) {
				totalPage = totalRows / rows;
			} else {
				totalPage = totalRows == 0 ? 1 : (totalRows / rows + 1);
			}
		} catch (Exception e) {
			return "error";
		}
		return "success";
	}

	 /***
	 * 管理人员 初始化查看系统识别的蛋白质信息
	 * @return
	 */
	public String viewProtein(){
		try {
			page = 1;
			rows = 10;
			setProteinList(proteinService.findallProteinList(page, rows));
			totalRows =  proteinService.countFirst();
			if (totalRows % rows == 0) {
				totalPage = totalRows / rows;
			} else {
				totalPage = totalRows == 0 ? 1 : (totalRows / rows + 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	/***
	 * 管理人员条件查找系统识别的蛋白质信息
	 */
	public String findProtein() {
		try {
			searchMode.setKeyWords(java.net.URLDecoder.decode(searchMode.getKeyWords(), "UTF-8"));
			proteinList=proteinService.findIndexList(searchMode, page, rows);
			totalRows=proteinService.countFindIndex(searchMode);
			if (totalRows % rows == 0) {
				totalPage = totalRows / rows;
			} else {
				totalPage = totalRows == 0 ? 1 : (totalRows / rows + 1);
			}
			//System.out.println("page"+page);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	
	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public List<String> getFindURLList() {
		return findURLList;
	}

	public void setFindURLList(List<String> findURLList) {
		this.findURLList = findURLList;
	}

	public void setSearchMode(SearchMode searchMode) {
		this.searchMode = searchMode;
	}

	public SearchMode getSearchMode() {
		return searchMode;
	}
	public int getMaxBufferedDocs() {
		return maxBufferedDocs;
	}
	public void setMaxBufferedDocs(int maxBufferedDocs) {
		this.maxBufferedDocs = maxBufferedDocs;
	}
	public void setCostSearchTime(long costSearchTime) {
		this.costSearchTime = costSearchTime;
	}
	public long getCostSearchTime() {
		return costSearchTime;
	}
	public void setProteinindexService(ProteinindexService proteinindexService) {
		this.proteinindexService = proteinindexService;
	}
	public ProteinindexService getProteinindexService() {
		return proteinindexService;
	}
	public void setDispaleList(List<DisplayData> dispaleList) {
		this.dispaleList = dispaleList;
	}
	public List<DisplayData> getDispaleList() {
		return dispaleList;
	}


	public IndexService getIndexService() {
		return indexService;
	}


	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}





	public static String getDataPath() {
		return dataPath;
	}
	public static void setDataPath(String dataPath) {
		IndexAction.dataPath = dataPath;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setProteinService(ProteinService proteinService) {
		this.proteinService = proteinService;
	}
	public ProteinService getProteinService() {
		return proteinService;
	}
	public void setProteinList(List<Protein> proteinList) {
		this.proteinList = proteinList;
	}
	public List<Protein> getProteinList() {
		return proteinList;
	}
	public Pindex getIndex() {
		return index;
	}
	public void setIndex(Pindex index) {
		this.index = index;
	}
	public List<Pindex> getIndexList() {
		return indexList;
	}
	public void setIndexList(List<Pindex> indexList) {
		this.indexList = indexList;
	}
	
	
}
