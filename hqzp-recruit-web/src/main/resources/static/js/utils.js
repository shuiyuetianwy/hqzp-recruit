/**
 * RecruitUtils — token/user storage and shared helpers.
 */
window.RecruitUtils = (function () {
    const TOKEN_KEY = 'recruit_token';
    const USER_KEY  = 'recruit_user';

    return {
        saveToken(token) { localStorage.setItem(TOKEN_KEY, token); },
        getToken()       { return localStorage.getItem(TOKEN_KEY); },
        removeToken()    { localStorage.removeItem(TOKEN_KEY); },

        saveUser(user)   { localStorage.setItem(USER_KEY, JSON.stringify(user)); },
        getUser()        {
            try { return JSON.parse(localStorage.getItem(USER_KEY)); }
            catch(e) { return null; }
        },
        removeUser()     { localStorage.removeItem(USER_KEY); },

        logout() {
            this.removeToken();
            this.removeUser();
            window.location.href = '/login';
        },

        isLoggedIn() { return !!this.getToken(); },

        requireLogin(redirectUrl) {
            if (!this.isLoggedIn()) {
                window.location.href = '/login?redirect=' + encodeURIComponent(redirectUrl || location.pathname);
                return false;
            }
            return true;
        }
    };
})();
