'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('job-portal', {
                parent: 'admin',
                url: '/job-portal',
                data: {
                    authorities: [],
                    pageTitle: 'job-portal.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobPortal/job-portal.html',
                        controller: 'JobPortalController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jobPortal');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('main');
                        $translatePartialLoader.addPart('job');
                        $translatePartialLoader.addPart('jobapplication');
                        $translatePartialLoader.addPart('jobApplicationStatus');
                        return $translate.refresh();
                    }]
                }
            })
            .state('jp-dashboard', {
                parent: 'entity',
                url: '/jp-dashboard',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_EMPLOYER','ROLE_JPADMIN','ROLE_JOBSEEKER'],
                    pageTitle: 'job-portal.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobPortal/dashboard.html',
                        controller: 'JpDashboardController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jobPortal');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('employer');
                        $translatePartialLoader.addPart('tempEmployer');
                        return $translate.refresh();
                    }]
                }
            })
            .state('jpReport', {
                parent: 'entity',
                url: '/reportModule/{module}',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_EMPLOYER','ROLE_JPADMIN','ROLE_JOBSEEKER'],
                    pageTitle: 'job-portal.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobPortal/jp-reports.html',
                        controller: 'JPReportController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jobPortal');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('tempEmployer');
                        return $translate.refresh();
                    }]
                }
            })
      ;
    });
