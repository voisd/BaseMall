package com.mall.app.utils.sqlUtils;


import com.mall.app.sqlbeen.History;
import com.mall.app.sqlbeen.HistoryDao;

import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */

public class SearchUtils {

    private static SearchUtils searchUtils;
    private static HistoryDao historyDao;
    public static final int searchCount = 20;

    public static SearchUtils getSingleTon(){
        if (searchUtils==null){
            searchUtils=new SearchUtils();
        }
        if(historyDao == null){
            historyDao=GreenDaoUtils.getSingleTon().getmDaoSession().getHistoryDao();
        }
        return searchUtils;
    }


    /***
     * 添加历史记录
     * */
    public void insertSearch(String searchText,int searchType,long searchTime){


        History history = seachHistory(searchText,searchType);
        if(history != null){
            delSearch(history);
        }
        history = new History();
        history.setSearchText(searchText);
        history.setSearchTime(searchTime);
        history.setSearchType(searchType);
        historyDao.insert(history);

        List<History> historyList = loadSearch();
        if(historyList.size() > searchCount){
            for(int i = 0;i<searchCount;i++){
                historyList.remove(0);
            }
            for(History history1:historyList){
                historyDao.delete(history1);
            }

        }

    }

    /***
     * 删除所有历史记录
     * */
    public void delSearch(){
        historyDao.deleteAll();
    }

    /***
     * 获取所有历史记录
     * */
    public List<History> loadSearch(){
        List<History> historyList = historyDao.queryBuilder().orderDesc(HistoryDao.Properties.Id).list();
        return historyList;
    }

    /***
     * 删除所有历史记录
     * */
    public void delSearch(History history){
        historyDao.delete(history);
    }


    /***
     * 获取所有历史记录
     * */
    public History seachHistory(String value,int searchType){
        Query query  = historyDao.queryBuilder().where(HistoryDao.Properties.SearchText.eq(value),HistoryDao.Properties.SearchType.eq(searchType)).build();
        if(query.list().size() > 0){
            return (History)query.list().get(0);
        }
        return null;
    }



}
