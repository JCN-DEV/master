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
        .state('instituteInfo.stuffInfo', {
            parent: 'instituteInfo',
            url: '/staff-info',
            data: {
                //authorities: ['ROLE_USER'],
                authorities: [],
                pageTitle: 'stepApp.instEmpCount.detail.title'
            },
            views: {
                'instituteView@instituteInfo': {
                    templateUrl: 'scripts/app/entities/instituteInformationSystem/StuffInfo/instEmpCount-detail.html',
                    controller: 'InstEmpCountDetailController',
                    controller: 'InstEmpCountDialogController'

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
                    //return InstEmpCount.get({id : $stateParams.id});
                      return InstEmpCount.get({id : 0});

                }]
            }
        })
        .state('instituteInfo.stuffInfo.new', {
            parent: 'instituteInfo.stuffInfo',
            url: '/new',
            data: {
                //authorities: ['ROLE_USER'],
                /*authorities: [],*/
            },
           views: {
                'instituteView@instituteInfo':{
                templateUrl: 'scripts/app/entities/instituteInformationSystem/StuffInfo/instEmpCount-dialog.html',
                      controller: 'InstEmpCountDialogController'
                }
           },
           resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('instGenInfo');
                    $translatePartialLoader.addPart('instEmpCount');
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
        .state('instituteInfo.stuffInfo.edit', {
            parent: 'instituteInfo.stuffInfo',
            url: '/{id}/edit',
            data: {
                /*authorities: ['ROLE_USER'],*/
            },
            views: {
                'instituteView@instituteInfo':{
                templateUrl: 'scripts/app/entities/instituteInformationSystem/StuffInfo/instEmpCount-dialog.html',
                      controller: 'InstEmpCountDialogController'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('instGenInfo');
                    $translatePartialLoader.addPart('instEmpCount');
                    return $translate.refresh();
                }],
                entity: ['InstEmpCount','$stateParams', function(InstEmpCount,$stateParams) {
                    return InstEmpCount.get({id : $stateParams.id});
                }]

            }
            /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                $modal.open({
                    templateUrl: 'scripts/app/entities/instituteInformationSystem/StuffInfo/instEmpCount-dialog.html',
                    controller: 'InstEmpCountDialogController',
                    size: 'lg',
                    resolve: {
                        entity: ['InstEmpCount', function(InstEmpCount) {
                            return InstEmpCount.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function(result) {
                    $state.go('instituteInfo.stuffInfo', null, { reload: true });
                }, function() {
                    $state.go('instituteInfo.stuffInfo');
                })
            }]*/
        })
        .state('instituteInfo.stuffInfo.delete', {
            parent: 'instituteInfo.stuffInfo',
            url: '/{id}/delete',
            data: {
                /*authorities: ['ROLE_USER'],*/
            },
            onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                $modal.open({
                    templateUrl: 'scripts/app/entities/instituteInformationSystem/StuffInfo/instEmpCount-delete-dialog.html',
                    controller: 'InstEmpCountDeleteController',
                    size: 'md',
                    resolve: {
                        entity: ['InstEmpCount', function(InstEmpCount) {
                            return InstEmpCount.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function(result) {
                    $state.go('instituteInfo.stuffInfo', null, { reload: true });
                }, function() {
                    $state.go('instituteInfo.stuffInfo');
                })
            }]
        })

        .state('instituteInfo.stuffInfo.approve', {
            parent: 'instituteInfo.approve',
            url: '/stuff-info-approve/{empId}',
            data: {
               // authorities: ['ROLE_ADMIN'],
                authorities: [],
            },
            onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                $modal.open({
                    templateUrl: 'scripts/app/entities/instituteInformationSystem/StuffInfo/instEmpCountInfo-approve-dialog.html',
                    controller: 'InstEmpCountApproveController',
                    size: 'md',
                    resolve: {
                        entity: ['InstEmpCount', function(InstEmpCount) {
                            return InstEmpCount.get({id : $stateParams.empId});
                        }]
                    }
                }).result.then(function(result) {
                    console.log(result);
                        $state.go('instituteInfo.approve',{},{reload:true});
                }, function() {
                        $state.go('instituteInfo.approve',{},{reload:true});
                })

            }]

        })
        .state('instituteInfo.stuffInfo.decline', {
            parent: 'instituteInfo.approve',
            url: '/{id}/staff-info-decline',
            data: {
                authorities: ['ROLE_ADMIN'],
            },
            onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                $modal.open({
                    templateUrl: 'scripts/app/entities/instituteInformationSystem/institute-decline.html',
                    controller: 'InstEmpCountDeclineController',
                    size: 'md',
                    resolve: {
                        entity: ['InstGenInfo', function(InstGenInfo) {
                            return InstGenInfo.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function(result) {
                        console.log(result);
                        $state.go('instituteInfo.approve', null, { reload: true });
                    }, function() {
                        $state.go('instituteInfo.approve');
                    })

            }]

        })
    });
