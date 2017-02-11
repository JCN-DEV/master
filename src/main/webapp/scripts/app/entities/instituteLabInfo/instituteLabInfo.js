'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instituteLabInfo', {
                parent: 'entity',
                url: '/instituteLabInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instituteLabInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instituteLabInfo/instituteLabInfos.html',
                        controller: 'InstituteLabInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instituteLabInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instituteLabInfo.detail', {
                parent: 'entity',
                url: '/instituteLabInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instituteLabInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instituteLabInfo/instituteLabInfo-detail.html',
                        controller: 'InstituteLabInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instituteLabInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstituteLabInfo', function($stateParams, InstituteLabInfo) {
                        return InstituteLabInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instituteLabInfo.new', {
                parent: 'instituteLabInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteLabInfo/instituteLabInfo-dialog.html',
                        controller: 'InstituteLabInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nameOrNumber: null,
                                    buildingNameOrNumber: null,
                                    length: null,
                                    width: null,
                                    totalBooks: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instituteLabInfo', null, { reload: true });
                    }, function() {
                        $state.go('instituteLabInfo');
                    })
                }]
            })
            .state('instituteLabInfo.edit', {
                parent: 'instituteLabInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteLabInfo/instituteLabInfo-dialog.html',
                        controller: 'InstituteLabInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstituteLabInfo', function(InstituteLabInfo) {
                                return InstituteLabInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteLabInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instituteLabInfo.delete', {
                parent: 'instituteLabInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteLabInfo/instituteLabInfo-delete-dialog.html',
                        controller: 'InstituteLabInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstituteLabInfo', function(InstituteLabInfo) {
                                return InstituteLabInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteLabInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
