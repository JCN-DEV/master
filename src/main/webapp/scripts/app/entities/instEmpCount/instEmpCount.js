'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
           /* .state('instEmpCount', {
                parent: 'entity',
                url: '/instEmpCounts',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmpCount.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmpCount/instEmpCounts.html',
                        controller: 'InstEmpCountController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmpCount');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })*/
            .state('instGenInfo.instEmpCount.detail', {
                parent: 'instGenInfo',
                url: '/instEmpCount/{id}',
                data: {
                    //authorities: ['ROLE_USER'],
                    authorities: [],
                    pageTitle: 'stepApp.instEmpCount.detail.title'
                },
                views: {
                    'instituteView@instGenInfo': {
                        templateUrl: 'scripts/app/entities/instEmpCount/instEmpCount-detail.html',
                        controller: 'InstEmpCountDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instEmpCount');
                        $translatePartialLoader.addPart('global');


                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstEmpCount', function($stateParams, InstEmpCount) {
                        return InstEmpCount.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instGenInfo.instEmpCount.new', {
                parent: 'instGenInfo',
                url: '/new',
                data: {
                    //authorities: ['ROLE_USER'],
                    authorities: [],
                },
               views: {
                    'instituteView@instGenInfo':{
                    templateUrl: 'scripts/app/entities/instEmpCount/instEmpCount-dialog.html',
                          controller: 'InstEmpCountDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instEmpCount');
//                        $translatePartialLoader.addPart('instLand');

                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            status: null,
                            id: null,
                            instBuilding:null,
                            instLand: null
                        };
                    }
                }

         })



            .state('instGenInfo.instEmpCount.edit', {
                parent: 'instGenInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmpCount/instEmpCount-dialog.html',
                        controller: 'InstEmpCountDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstEmpCount', function(InstEmpCount) {
                                return InstEmpCount.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmpCount', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instGenInfo.instEmpCount.delete', {
                parent: 'instGenInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmpCount/instEmpCount-delete-dialog.html',
                        controller: 'InstEmpCountDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstEmpCount', function(InstEmpCount) {
                                return InstEmpCount.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmpCount', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
