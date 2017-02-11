'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('jobPlacementInfo', {
                parent: 'entity',
                url: '/jobPlacementInfos',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.jobPlacementInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobPlacementInfo/jobPlacementInfos.html',
                        controller: 'JobPlacementInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jobPlacementInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('jobPlacementInfo.detail', {
                parent: 'entity',
                url: '/jobPlacementInfo/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.jobPlacementInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobPlacementInfo/jobPlacementInfo-detail.html',
                        controller: 'JobPlacementInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jobPlacementInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JobPlacementInfo', function($stateParams, JobPlacementInfo) {
                        return JobPlacementInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('jobPlacementInfo.new', {
                parent: 'jobPlacementInfo',
                url: '/new',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobPlacementInfo/jobPlacementInfo-dialog.html',
                        controller: 'JobPlacementInfoDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            jobId: null,
                            orgName: null,
                            description: null,
                            address: null,
                            designation: null,
                            department: null,
                            responsibility: null,
                            workFrom: null,
                            workTo: null,
                            currWork: null,
                            experience: null,
                            createDate: null,
                            createBy: null,
                            updateBy: null,
                            updateDate: null,
                            id: null
                        };
                    }
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/jobPlacementInfo/jobPlacementInfo-dialog.html',
                        controller: 'JobPlacementInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    jobId: null,
                                    orgName: null,
                                    description: null,
                                    address: null,
                                    designation: null,
                                    department: null,
                                    responsibility: null,
                                    workFrom: null,
                                    workTo: null,
                                    currWork: null,
                                    experience: null,
                                    createDate: null,
                                    createBy: null,
                                    updateBy: null,
                                    updateDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('jobPlacementInfo', null, { reload: true });
                    }, function() {
                        $state.go('jobPlacementInfo');
                    })
                }]*/
            })
            .state('jobPlacementInfo.edit', {
                parent: 'jobPlacementInfo',
                url: '/{id}/edit',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobPlacementInfo/jobPlacementInfo-dialog.html',
                        controller: 'JobPlacementInfoDialogController'
                    }
                },
                resolve: {
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart('leftmenu');
                            return $translate.refresh();
                        }],
                        entity: ['$stateParams', 'JobPlacementInfo', function($stateParams, JobPlacementInfo) {
                        return JobPlacementInfo.get({id : $stateParams.id});
                    }]
                }

                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/jobPlacementInfo/jobPlacementInfo-dialog.html',
                        controller: 'JobPlacementInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['JobPlacementInfo', function(JobPlacementInfo) {
                                return JobPlacementInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('jobPlacementInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            })
            .state('jobPlacementInfo.delete', {
                parent: 'jobPlacementInfo',
                url: '/{id}/delete',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/jobPlacementInfo/jobPlacementInfo-delete-dialog.html',
                        controller: 'JobPlacementInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['JobPlacementInfo', function(JobPlacementInfo) {
                                return JobPlacementInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('jobPlacementInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
