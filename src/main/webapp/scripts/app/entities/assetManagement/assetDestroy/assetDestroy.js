'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('assetDestroy', {
                parent: 'assetManagements',
                url: '/assetDestroys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetDestroy.home.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetDestroy/assetDestroys.html',
                        controller: 'AssetDestroyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetDestroy');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('assetDestroy.detail', {
                parent: 'assetManagements',
                url: '/assetDestroy/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetDestroy.detail.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetDestroy/assetDestroy-detail.html',
                        controller: 'AssetDestroyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetDestroy');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetDestroy', function($stateParams, AssetDestroy) {
                        return AssetDestroy.get({id : $stateParams.id});
                    }]
                }
            })
            .state('assetDestroy.new', {
                parent: 'assetDestroy',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetDestroy.home.title'
                },

                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetDestroy/assetDestroy-dialog.html',
                        controller: 'AssetDestroyDialogController'
                    }
                },
                        resolve: {
                            entity: function () {
                                return {
                                    transferReference: null,
                                    destroyDate: null,
                                    usedPeriod: null,
                                    causeOfDate: null,
                                    id: null
                                };
                            }
                        }
                //onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                //    $modal.open({
                //        templateUrl: 'scripts/app/entities/assetManagement/assetDestroy/assetDestroy-dialog.html',
                //        controller: 'AssetDestroyDialogController',
                //        size: 'lg',
                //        resolve: {
                //            entity: function () {
                //                return {
                //                    transferReference: null,
                //                    destroyDate: null,
                //                    usedPeriod: null,
                //                    causeOfDate: null,
                //                    id: null
                //                };
                //            }
                //        }
                //    }).result.then(function(result) {
                //        $state.go('assetDestroy', null, { reload: true });
                //    }, function() {
                //        $state.go('assetDestroy');
                //    })
                //}]
            })
            .state('assetDestroy.edit', {
                parent: 'assetManagements',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetDestroy.home.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl:'scripts/app/entities/assetManagement/assetDestroy/assetDestroy-dialog.html',
                        controller: 'AssetDestroyDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetDestroy');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetDestroy', function($stateParams, AssetDestroy) {
                        return AssetDestroy.get({id : $stateParams.id});
                    }]
                }
                //onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                //    $modal.open({
                //        templateUrl: 'scripts/app/entities/assetManagement/assetDestroy/assetDestroy-dialog.html',
                //        controller: 'AssetDestroyDialogController',
                //        size: 'lg',
                //        resolve: {
                //            entity: ['AssetDestroy', function(AssetDestroy) {
                //                return AssetDestroy.get({id : $stateParams.id});
                //            }]
                //        }
                //    }).result.then(function(result) {
                //        $state.go('assetDestroy', null, { reload: true });
                //    }, function() {
                //        $state.go('^');
                //    })
                //}]
            });
    });
