package cn.edu.nwsuaf.Action;
import java.util.Date;
import java.util.List;



import cn.edu.nwsuaf.Model.DisplayData;
import cn.edu.nwsuaf.Model.SearchMode;
import cn.edu.nwsuaf.Service.Impl.IndexService;
import cn.edu.nwsuaf.Service.Impl.ProteinindexService;

public class SearchAction {
	//private static String dataPath=ServletActionContext.getServletContext().getRealPath("/")+"\\Lucene Text";
	
	private ProteinindexService proteinindexService;
	private IndexService indexService;
	
	private String keyWords;// 存入搜索的关键字信息
	private SearchMode searchMode = new SearchMode();
	
	private int rowCount;// 记录数
	private int pages;// 总页数
	private int currentPage;// 当前页数
	private int pageSize; // 每页记录数
	
	private List<String> findURLList;
	private List<DisplayData> dispaleList;
        
    /** 索引页面缓冲 */    
    private int maxBufferedDocs = 500;     
	
    private long costSearchTime;
    
    /***
     * 下放到userAction中进行操作
     * @return
     */
	/*public String createIndex(){
		try {
			proteinindexService.createIndex(dataPath);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}*/
   
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
			dispaleList=proteinindexService.searchByIndex(searchMode, currentPage, pageSize);
			rowCount=proteinindexService.searchByIndexCount(searchMode,currentPage, pageSize);
			 long endTime = new Date().getTime();
			 costSearchTime=endTime-startTime;
			if (rowCount % pageSize == 0) {
				pages = rowCount / pageSize;
			} else {
				pages = rowCount == 0 ? 1 : (rowCount / pageSize + 1);
			}
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

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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
	
}
