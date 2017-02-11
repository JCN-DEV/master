'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('jobPostingInfo', {
                parent: 'entity',
                url: '/jobPostingInfos',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.jobPostingInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobPostingInfo/jobPostingInfos.html',
                        controller: 'JobPostingInfoController'
                    }
                },

                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jobPostingInfo');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('leftmenu');
                        return $translate.refresh();
                    }]
                }
            })

            .state('jobSuggestions', {
                parent: 'entity',
                url: '/jobSuggestions',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.jobPostingInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobPostingInfo/jobSuggestions.html',
                        controller: 'JobPostingInfoController'
                    },
                    'ViewCategory@cat': {
                        templateUrl: 'scripts/app/entities/jobPostingInfo/category.html',
                        controller: 'CatController'
                    }

                },

                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jobPostingInfo');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('leftmenu');
                        return $translate.refresh();
                    }]
                }
            })
            .state("Category", {
                views:{
                    "content": {
                        /*templateUrl: "modal.html"*/
                        templateUrl: 'scripts/app/entities/jobPostingInfo/category.html',
                        controller: 'CatController'
                    }
                },
                abstract: true
            })
            .state('jobPostingInfo.detail', {
                parent: 'entity',
                url: '/jobPostingInfo/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.jobPostingInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobPostingInfo/jobPostingInfo-detail.html',
                        controller: 'JobPostingInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jobPostingInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JobPostingInfo', function($stateParams, JobPostingInfo) {
                        return JobPostingInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('jobPostingInfo.new', {
                parent: 'jobPostingInfo',
                url: '/new',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobPostingInfo/jobPostingInfo-dialog.html',
                        controller: 'JobPostingInfoDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            jobPostId: null,
                            jobTitle: null,
                            organizatinName: null,
                            jobVacancy: null,
                            salaryRange: null,
                            jobSource: null,
                            publishedDate: null,
                            applicationDateLine: null,
                            jobDescription: null,
                            jobFileName: null,
                            upload: null,
                            uploadContentType: null,
                            createDate: null,
                            createBy: null,
                            updateBy: null,
                            updateDate: null,
                            id: null
                        };
                    }
                }

            })
            .state('jobPostingInfo.edit', {
                parent: 'jobPostingInfo',
                url: '/{id}/edit',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobPostingInfo/jobPostingInfo-dialog.html',
                        controller: 'JobPostingInfoDialogController'
                    }
                },
                resolve: {
                    entity: ['JobPostingInfo','$stateParams', function(JobPostingInfo,$stateParams) {
                        return JobPostingInfo.get({id : $stateParams.id});
                    }]
                }

            })
            .state('jobPostingInfo.delete', {
                parent: 'jobPostingInfo',
                url: '/{id}/delete',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/jobPostingInfo/jobPostingInfo-delete-dialog.html',
                        controller: 'JobPostingInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['JobPostingInfo', function(JobPostingInfo) {
                                return JobPostingInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('jobPostingInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
