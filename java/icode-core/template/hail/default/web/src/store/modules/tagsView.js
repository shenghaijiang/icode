const tagsView = {
  state: {
    visitedViews: [],
    cachedViews: []
  },
  mutations: {
    ADD_VISITED_VIEWS: (state, view) => {
      const item = state.visitedViews.some((v) => v.path === view.path);
      if (item) {return;}
      if (view.path === "/") {return;}
      state.visitedViews.push({
        name: view.name,
        path: view.path,
        title: view.meta.title
      });
      if (!view.meta.noCache) {
        state.cachedViews.push(view.name);
      }
    },
    DEL_VISITED_VIEWS: (state, view) => {
      for (const [i, v] of state.visitedViews.entries()) {
        if (v.path === view.path) {
          state.visitedViews.splice(i, 1);
          break;
        }
      }
      for (const i of state.cachedViews) {
        if (i === view.name) {
          const index = state.cachedViews.indexOf(i);
          state.cachedViews.splice(index, index + 1);
          break;
        }
      }
    },
    DEL_OTHERS_VIEWS: (state, view) => {
      for (const [i, v] of state.visitedViews.entries()) {
        if (v.path === view.path) {
          state.visitedViews = state.visitedViews.slice(i, i + 1);
          break;
        }
      }
      for (const i of state.cachedViews) {
        if (i === view.name) {
          const index = state.cachedViews.indexOf(i);
          state.cachedViews = state.cachedViews.slice(index, i + 1);
          break;
        }
      }
    },
    DEL_ALL_VIEWS: (state) => {
      state.visitedViews = [];
      state.cachedViews = [];
    },
    CHANGE_CATCHD_VIEWS: (state, route) => {
      // CHANGE_CATCHD_VIEWS
    }
  },
  actions: {
    addVisitedViews({commit}, view) {
      commit("ADD_VISITED_VIEWS", view);
    },
    delVisitedViews({ commit, state }, view) {
      return new Promise((resolve) => {
        commit("DEL_VISITED_VIEWS", view);
        resolve([...state.visitedViews]);
      });
    },
    delOthersViews({ commit, state }, view) {
      return new Promise((resolve) => {
        commit("DEL_OTHERS_VIEWS", view);
        resolve([...state.visitedViews]);
      });
    },
    delAllViews({ commit, state }) {
      return new Promise((resolve) => {
        commit("DEL_ALL_VIEWS");
        resolve([...state.visitedViews]);
      });
    },
    cachedViewsChange({ commit, state }, route) {
      return new Promise((resolve) => {
        commit("CHANGE_CATCHD_VIEWS", route);
        resolve([...state.cachedViews]);
      });
    }
  }
};

export default tagsView;