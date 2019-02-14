
window.TOKEN_KEY = "app-token";

window.SYSTEM_NAME = "${model.name}";

window.APP_SETTING = {
    SERVER_HOST: "192.168.2.200",
    SERVER_PORT: 7001,
    XTP: {
        SERVER_HOST: "119.37.194.4",
        SERVER_PORT: 5555,
        API: "xtp-api"
    },
    ${model.packageName?keep_after_last(".")?upper_case}: {
        SERVER_HOST: "119.37.194.4",
        SERVER_PORT: 5555,
        API: "${model.packageName?keep_after_last(".")?lower_case}-api"
    }
};
