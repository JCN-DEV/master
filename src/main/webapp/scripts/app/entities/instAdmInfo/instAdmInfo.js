'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            /*.state('instGenInfo.instAdmInfo', {
                parent: 'instGenInfo',
                url: '/instAdmInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instAdmInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instAdmInfo/instAdmInfos.html',
                        controller: 'InstAdmInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instAdmInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })*/
            .state('instGenInfo.instAdmInfo.detail', {
                parent: 'instGenInfo',
                url: '/inst-Admin-Info/{id}',
                data: {
                    /*authorities: ['ROLE_USER'],*/
                    authorities: [],
                    pageTitle: 'stepApp.instAdmInfo.detail.title'
                },
                views: {
                    'instituteView@instGenInfo':{
                         templateUrl: 'scripts/app/entities/instAdmInfo/instAdmInfo-detail.html',
                         controller: 'InstAdmInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instAdmInfo');

                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstAdmInfo', function($stateParams, InstAdmInfo) {
                        return InstAdmInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instGenInfo.instAdmInfo.new', {
                parent: 'instGenInfo',
                url: '/new-admin-info',
                data: {
                    authorities: ['ROLE_INSTITUTE']
                },
                views: {
                    'instituteView@instGenInfo':{
                        templateUrl: 'scripts/app/entities/instAdmInfo/instAdmInfo-dialog.html',
                        controller: 'InstAdmInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instAdmInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            adminCounselorName: null,
                            counselorMobileNo: null,
                            insHeadName: null,
                            insHeadMobileNo: null,
                            deoName: null,
                            deoMobileNo: null,
                            status: null,
                            id: null
                        };
                    }
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instAdmInfo/instAdmInfo-dialog.html',
                        controller: 'InstAdmInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    adminCounselorName: null,
                                    counselorMobileNo: null,
                                    insHeadName: null,
                                    insHeadMobileNo: null,
                                    deoName: null,
                                    deoMobileNo: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instGenInfo.instAdmInfo', null, { reload: true });
                    }, function() {
                        $state.go('instGenInfo.instAdmInfo');
                    })
                }]*/
            })
            .state('instGenInfo.instAdmInfo.edit', {
                parent: 'instGenInfo',
                url: '/{id}/edit-admin-info',
                data: {
                    /*authorities: ['ROLE_USER'],*/
                    authorities: []
                },
                views: {
                    'instituteView@instGenInfo':{
                        templateUrl: 'scripts/app/entities/instAdmInfo/instAdmInfo-dialog.html',
                        controller: 'InstAdmInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['InstAdmInfo', function(InstAdmInfo) {
                        return InstAdmInfo.get({id : $stateParams.id});
                    }]
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instAdmInfo/instAdmInfo-dialog.html',
                        controller: 'InstAdmInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstAdmInfo', function(InstAdmInfo) {
                                return InstAdmInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instGenInfo.instAdmInfo', null, { reload: true });
                    }, function() {
                        $state.go('instGenInfo.instAdmInfo');
                    })
                }]*/
            })
            .state('instGenInfo.instAdmInfo.delete', {
                parent: 'instGenInfo',
                url: '/{id}/delete-admin-info',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instAdmInfo/instAdmInfo-delete-dialog.html',
                        controller: 'InstAdmInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstAdmInfo', function(InstAdmInfo) {
                                return InstAdmInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instGenInfo.instAdmInfo', null, { reload: true });
                         $state.go('instGenInfo.instAdmInfo');
                    }, function() {
                        $state.go('instGenInfo.instAdmInfo');
                    })
                }]
            })
            .state('instGenInfo.instAdmInfo.approve', {
                            parent: 'instGenInfo',
                            url: '/{id}/approve-Admin-Info',
                            data: {
                                authorities: ['ROLE_ADMIN'],
                            },
                            onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                                $modal.open({
                                    templateUrl: 'scripts/app/entities/instAdmInfo/instAdmInfo-approve-dialog.html',
                                    controller: 'InstAdmInfoApproveController',
                                    size: 'md',
                                    resolve: {
                                        entity: ['InstAdmInfo', function(InstAdmInfo) {
                                            return InstAdmInfo.get({id : $stateParams.id});
                                          //  return InstGenInfo.get({id : $stateParams.id});
                                        }]
                                    }
                                }).result.then(function(result) {
                                        console.log(result);
                                        $state.go('instGenInfo.instAdmInfo', null, { reload: true });
                                    }, function() {
                                        $state.go('instGenInfo.instAdmInfo');
                                    })

                            }]

                        });
    });
