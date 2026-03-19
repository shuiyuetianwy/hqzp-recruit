/**
 * Renders the navbar user area based on auth state.
 * Runs after DOM is ready.
 */
(function () {
    document.addEventListener('DOMContentLoaded', function () {
        const container = document.getElementById('navbar-user');
        if (!container) return;

        const user = RecruitUtils.getUser();
        if (!user) {
            container.innerHTML = `
                <a href="/login" class="nav-btn">登录</a>
                <a href="/register" class="nav-btn nav-btn-primary">注册</a>`;
            return;
        }

        const dashboardUrl = user.userType === 3 ? '/candidate/dashboard' : '/hr/dashboard';
        container.innerHTML = `
            <a href="${dashboardUrl}" class="nav-user-info">
                <img src="${user.avatar || '/images/default-avatar.png'}"
                     class="nav-avatar" alt="" onerror="this.src='/images/default-avatar.png'"/>
                <span>${user.nickname || user.username}</span>
            </a>
            <a href="#" class="nav-btn" id="logout-btn">退出</a>`;

        document.getElementById('logout-btn').addEventListener('click', function (e) {
            e.preventDefault();
            RecruitApi.logout().finally(() => RecruitUtils.logout());
        });
    });
})();
