'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('assetCategorySetup', {
                parent: 'assetManagements',
                url: '/assetCategorySetups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetCategorySetup.home.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetCategorySetup/assetCategorySetups.html',
                        controller: 'AssetCategorySetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetCategorySetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('assetCategorySetup.detail', {
                parent: 'assetCategorySetup',
                url: '/assetCategorySetup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetCategorySetup.detail.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetCategorySetup/assetCategorySetup-detail.html',
                        controller: 'AssetCategorySetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetCategorySetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetCategorySetup', function($stateParams, AssetCategorySetup) {
                        return AssetCategorySetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('assetCategorySetup.new', {
                parent: 'assetCategorySetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetCategorySetup.detail.title'
                },

                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetCategorySetup/assetCategorySetup-dialog.html',
                        controller: 'AssetCategorySetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetCategorySetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetCategorySetup', function($stateParams, AssetCategorySetup) {
                    return {
                             categoryName: null,
                             description: null,
                             status: null,
                             id: null
                          };
                    }]
                }

                //onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                //    $modal.open({
                //        templateUrl: 'scripts/app/entities/assetManagement/assetCategorySetup/assetCategorySetup-dialog.html',
                //        controller: 'AssetCategorySetupDialogController',
                //        size: 'lg',
                //        resolve: {
                //            entity: function () {
                //                return {
                //                    categoryName: null,
                //                    description: null,
                //                    status: null,
                //                    id: null
                //                };
                //            }
                //        }
                //    }).result.then(function(result) {
                //        $state.go('assetCategorySetup', null, { reload: true });
                //    }, function() {
                //        $state.go('assetCategorySetup');
                //    })
                //}]
            })
            .state('assetCategorySetup.edit', {
                parent: 'assetCategorySetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetCategorySetup.detail.title'
                },


                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetCategorySetup/assetCategorySetup-dialog.html',
                        controller: 'AssetCategorySetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetCategorySetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetCategorySetup', function($stateParams, AssetCategorySetup) {
                        console.log("stateParam"+AssetCategorySetup.get({id : $stateParams.id}));
                        return AssetCategorySetup.get({id : $stateParams.id});
                    }]
                }

                //onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                //    $modal.open({
                //        templateUrl: 'scripts/app/entities/assetManagement/assetCategorySetup/assetCategorySetup-dialog.html',
                //        controller: 'AssetCategorySetupDialogController',
                //        size: 'lg',
                //        resolve: {
                //            entity: ['AssetCategorySetup', function(AssetCategorySetup) {
                //                return AssetCategorySetup.get({id : $stateParams.id});
                //            }]
                //        }
                //    }).result.then(function(result) {
                //        $state.go('assetCategorySetup', null, { reload: true });
                //    }, function() {
                //        $state.go('^');
                //    })
                //}]
            });
    });
