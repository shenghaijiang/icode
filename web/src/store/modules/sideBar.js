

const sideBar={
    state:{
        isCollapse:false,
    },
    mutations:{
        CHANGE_ISCOLLAPSE:(state) => {
            state.isCollapse=!state.isCollapse;
        }
    },
    actions:{
        changeIsCollapse({commit}){
            commit('CHANGE_ISCOLLAPSE')
        },
    }
}

export default sideBar