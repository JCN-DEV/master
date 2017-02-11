'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('tempInstGenInfo', {
                parent: 'entity',
                url: '/tempInstGenInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.tempInstGenInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tempInstGenInfo/tempInstGenInfos.html',
                        controller: 'TempInstGenInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tempInstGenInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('tempInstGenInfo.detail', {
                parent: 'entity',
                url: '/tempInstGenInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.tempInstGenInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tempInstGenInfo/tempInstGenInfo-detail.html',
                        controller: 'TempInstGenInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tempInstGenInfo');
                        $translatePartialLoader.addPart('instType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TempInstGenInfo', function($stateParams, TempInstGenInfo) {
                        return TempInstGenInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('tempInstGenInfo.new', {
                parent: 'tempInstGenInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tempInstGenInfo/tempInstGenInfo-dialog.html',
                        controller: 'TempInstGenInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tempInstGenInfo');
                        $translatePartialLoader.addPart('instType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TempInstGenInfo', function($stateParams, TempInstGenInfo) {
                        return TempInstGenInfo.get({id : $stateParams.id});
                    }]
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/tempInstGenInfo/tempInstGenInfo-dialog.html',
                        controller: 'TempInstGenInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    user: null,
                                    instGenInfo: null,
                                    name: null,
                                    type: null,
                                    village: null,
                                    postOffice: null,
                                    postCode: null,
                                    landPhone: null,
                                    mobileNo: null,
                                    email: null,
                                    consArea: null,
                                    update: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('tempInstGenInfo', null, { reload: true });
                    }, function() {
                        $state.go('tempInstGenInfo');
                    })
                }]*/
            })
            .state('tempInstGenInfo.edit', {
                parent: 'tempInstGenInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/tempInstGenInfo/tempInstGenInfo-dialog.html',
                        controller: 'TempInstGenInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TempInstGenInfo', function(TempInstGenInfo) {
                                return TempInstGenInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('tempInstGenInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('tempInstGenInfo.delete', {
                parent: 'tempInstGenInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/tempInstGenInfo/tempInstGenInfo-delete-dialog.html',
                        controller: 'TempInstGenInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['TempInstGenInfo', function(TempInstGenInfo) {
                                return TempInstGenInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('tempInstGenInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
