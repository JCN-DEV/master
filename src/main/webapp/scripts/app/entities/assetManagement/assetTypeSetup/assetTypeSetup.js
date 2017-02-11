'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('assetTypeSetup', {
                parent: 'assetManagements',
                url: '/assetTypeSetups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetTypeSetup.home.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetTypeSetup/assetTypeSetups.html',
                        controller: 'AssetTypeSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetTypeSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('assetTypeSetup.detail', {
                parent: 'assetTypeSetup',
                url: '/assetTypeSetup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetTypeSetup.detail.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetTypeSetup/assetTypeSetup-detail.html',
                        controller: 'AssetTypeSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetTypeSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetTypeSetup', function($stateParams, AssetTypeSetup) {
                        return AssetTypeSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('assetTypeSetup.new', {
                parent: 'assetTypeSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetTypeSetup.detail.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetTypeSetup/assetTypeSetup-dialog.html',
                        controller: 'AssetTypeSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetTypeSetup');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            typeName: null,
                            description: null,
                            status: true,
                            id: null
                        };
                    }
                }
            })







            //    onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
            //        $modal.open({
            //            templateUrl: 'scripts/app/entities/assetManagement/assetTypeSetup/assetTypeSetup-dialog.html',
            //            controller: 'AssetTypeSetupDialogController',
            //            size: 'lg',
            //            resolve: {
            //                entity: function () {
            //                    return {
            //                        typeName: null,
            //                        description: null,
            //                        status: null,
            //                        id: null
            //                    };
            //                }
            //            }
            //        }).result.then(function(result) {
            //            $state.go('assetTypeSetup', null, { reload: true });
            //        }, function() {
            //            $state.go('assetTypeSetup');
            //        })
            //    }]
            //})



            .state('assetTypeSetup.edit', {
                parent: 'assetTypeSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetTypeSetup.detail.title'
                },

                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetTypeSetup/assetTypeSetup-dialog.html',
                        controller: 'AssetTypeSetupDialogController'
                    }
                },



                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetTypeSetup');
                        return $translate.refresh();
                    }],


                    entity: ['$stateParams','AssetTypeSetup', function($stateParams, AssetTypeSetup) {

                                       return AssetTypeSetup.get({id : $stateParams.id});
                                    }]

                    //entity: function () {
                    //    return {
                    //        typeName: null,
                    //        description: null,
                    //        status: null,
                    //        id: null
                    //    };
                    //}
                }


                //onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                //    $modal.open({
                //        templateUrl: 'scripts/app/entities/assetManagement/assetTypeSetup/assetTypeSetup-dialog.html',
                //        controller: 'AssetTypeSetupDialogController',
                //        size: 'lg',
                //        resolve: {
                //            entity: ['AssetTypeSetup', function(AssetTypeSetup) {
                //                return AssetTypeSetup.get({id : $stateParams.id});
                //            }]
                //        }
                //    }).result.then(function(result) {
                //        $state.go('assetTypeSetup', null, { reload: true });
                //    }, function() {
                //        $state.go('^');
                //    })
                //}]

            });
    });
