/**
 * Created by lugy on 2017/6/15.
 */
export const globleConstant={
    defaultPageSize:9999999,
    getUserName:()=>{
        let user = localStorage.getItem('user');
        if (user) {
            return user;
        }else{
            return '';
        }
    }
};

//数据字典必须固定的字段
export const dictFormat={
    dictType:'DICTTYPE',
    codingRule:'CODINGRULE'
};

//搜索框时间的长度
export const searchStyle={
    dateStyle:"width:130px",
    statusStyle:"width:100px"
}