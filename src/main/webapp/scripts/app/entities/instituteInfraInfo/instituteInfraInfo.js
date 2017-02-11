'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instituteInfraInfo', {
                parent: 'entity',
                url: '/instituteInfraInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instituteInfraInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instituteInfraInfo/instituteInfraInfos.html',
                        controller: 'InstituteInfraInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instituteInfraInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instituteInfraInfo.detail', {
                parent: 'entity',
                url: '/instituteInfraInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instituteInfraInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instituteInfraInfo/instituteInfraInfo-detail.html',
                        controller: 'InstituteInfraInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instituteInfraInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstituteInfraInfo', function($stateParams, InstituteInfraInfo) {
                        return InstituteInfraInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instituteInfraInfo.new', {
                parent: 'instituteInfraInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInfraInfo/instituteInfraInfo-dialog.html',
                        controller: 'InstituteInfraInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instituteInfraInfo', null, { reload: true });
                    }, function() {
                        $state.go('instituteInfraInfo');
                    })
                }]
            })
            .state('instituteInfraInfo.edit', {
                parent: 'instituteInfraInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInfraInfo/instituteInfraInfo-dialog.html',
                        controller: 'InstituteInfraInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstituteInfraInfo', function(InstituteInfraInfo) {
                                return InstituteInfraInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteInfraInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instituteInfraInfo.delete', {
                parent: 'instituteInfraInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInfraInfo/instituteInfraInfo-delete-dialog.html',
                        controller: 'InstituteInfraInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstituteInfraInfo', function(InstituteInfraInfo) {
                                return InstituteInfraInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteInfraInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
