'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('jobapplication', {
                parent: 'entity',
                url: '/jobapplications',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jobapplication.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobapplication/jobapplications.html',
                        controller: 'JobapplicationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jobapplication');
                        $translatePartialLoader.addPart('jobApplicationStatus');
                        $translatePartialLoader.addPart('cvType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('jobapplication.detail', {
                parent: 'entity',
                url: '/jobapplication/{id}',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jobapplication.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobapplication/jobapplication-detail.html',
                        controller: 'JobapplicationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jobapplication');
                        $translatePartialLoader.addPart('jobApplicationStatus');
                        $translatePartialLoader.addPart('cvType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Jobapplication', function($stateParams, Jobapplication) {
                        return Jobapplication.get({id : $stateParams.id});
                    }]
                }
            })
            .state('jobapplication.new', {
                parent: 'jobapplication',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/jobapplication/jobapplication-dialog.html',
                        controller: 'JobapplicationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    note: null,
                                    cv: null,
                                    cvContentType: null,
                                    expectedSalary: null,
                                    applicantStatus: null,
                                    cvType: null,
                                    appliedDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('jobapplication', null, { reload: true });
                    }, function() {
                        $state.go('jobapplication');
                    })
                }]
            })
            .state('jobapplication.edit', {
                parent: 'jobapplication',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/jobapplication/jobapplication-dialog.html',
                        controller: 'JobapplicationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Jobapplication', function(Jobapplication) {
                                return Jobapplication.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('jobapplication', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('appliedJobs', {
                parent: 'entity',
                url: '/jobapplied',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jobapplication.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobapplication/appliedJobs.html',
                        controller: 'JobapplicationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jobapplication');
                        $translatePartialLoader.addPart('job');
                        $translatePartialLoader.addPart('cvType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            });
    });
