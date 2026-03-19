/**
 * RecruitApi — centralised Axios wrapper for all REST calls.
 * Every method returns a Promise resolving to response.data (the R<T> wrapper).
 */
(function () {
    // Axios instance with auth header injection
    const http = axios.create({ baseURL: '/api', timeout: 30000 });

    http.interceptors.request.use(config => {
        const token = RecruitUtils.getToken();
        if (token) config.headers['Authorization'] = 'Bearer ' + token;
        return config;
    });

    http.interceptors.response.use(
        res => {
            const data = res.data;
            if (data && data.code !== undefined && data.code !== 200) {
                if (data.code === 401) { RecruitUtils.logout(); return Promise.reject(res); }
                return Promise.reject({ response: { data } });
            }
            return res.data;
        },
        err => {
            if (err.response?.status === 401) RecruitUtils.logout();
            return Promise.reject(err);
        }
    );

    window.RecruitApi = {
        // ── Auth ──────────────────────────────────────────────
        login:    (data)       => http.post('/auth/login', data),
        register: (data)       => http.post('/auth/register', data),
        logout:   ()           => http.post('/auth/logout'),

        // ── Jobs ──────────────────────────────────────────────
        getJobs:      (params) => http.get('/jobs', { params }),
        getJobDetail: (id)     => http.get(`/jobs/${id}`),
        createJob:    (data)   => http.post('/jobs', data),
        updateJob:    (id, d)  => http.put(`/jobs/${id}`, d),
        publishJob:   (id)     => http.put(`/jobs/${id}/publish`),
        closeJob:     (id)     => http.put(`/jobs/${id}/close`),
        deleteJob:    (id)     => http.delete(`/jobs/${id}`),

        // ── Resumes ───────────────────────────────────────────
        getMyResume:  ()       => http.get('/resumes/mine'),
        getResume:    (id)     => http.get(`/resumes/${id}`),
        saveResume:   (data)   => http.post('/resumes', data),
        deleteResume: (id)     => http.delete(`/resumes/${id}`),

        // ── Applications ──────────────────────────────────────
        applyJob:                (data)     => http.post('/applications', data),
        getCandidateApplications:(params)   => http.get('/applications/candidate', { params }),
        getHrApplications:       (params)   => http.get('/applications/hr', { params }),
        updateApplicationStatus: (id, data) => http.put(`/applications/${id}/status`, data),
        scheduleInterview:       (id, data) => http.put(`/applications/${id}/interview`, data),
        abandonApplication:      (id)       => http.put(`/applications/${id}/abandon`),

        // ── Companies ─────────────────────────────────────────
        getCompanies:    (params) => http.get('/companies', { params }),
        getCompanyDetail:(id)     => http.get(`/companies/${id}`),
        saveCompany:     (data)   => http.post('/companies', data),

        // ── Files ─────────────────────────────────────────────
        uploadAvatar:    (file)   => {
            const fd = new FormData(); fd.append('file', file);
            return http.post('/files/avatar', fd, { headers: { 'Content-Type': 'multipart/form-data' } });
        },
        uploadResume:    (file, resumeId) => {
            const fd = new FormData(); fd.append('file', file);
            if (resumeId) fd.append('resumeId', resumeId);
            return http.post('/files/resume', fd, { headers: { 'Content-Type': 'multipart/form-data' } });
        },
        getPresignedUrl: (fileKey) => http.get('/files/presigned-url', { params: { fileKey } }),

        // ── AI ────────────────────────────────────────────────
        aiAnalyseResume:  (resumeId)  => http.post(`/ai/resume/analyse/${resumeId}`),
        aiMatchJobResume: (data)      => http.post('/ai/match', data),
        aiInterviewQs:    (data)      => http.post('/ai/interview-questions', data),
        aiCareerAdvice:   (data)      => http.post('/ai/career-advice', data),
        aiOptimiseResume: (data)      => http.post('/ai/resume/optimise', data),
    };
})();
