'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instituteInfo.adminInfo', {
                parent: 'instituteInfo',
                url: '/admin-info',
                data: {
                    pageTitle: 'stepApp.instAdmInfo.detail.title'
                },
                views: {
                    'instituteView@instituteInfo':{
                         templateUrl: 'scripts/app/entities/instituteInformationSystem/AdminInfo/instAdmInfo-detail.html',
                         controller: 'InstAdmInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instAdmInfo');
                        $translatePartialLoader.addPart('instGovernBody');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', function($stateParams) {
                        return null;
                    }]
                }
            })
            .state('instituteInfo.adminInfo.new', {
                parent: 'instituteInfo.adminInfo',
                url: '/new',
                data: {
                },
                views: {
                    'instituteView@instituteInfo':{
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/AdminInfo/instAdmInfo-dialog.html',
                        controller: 'InstAdmInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instAdmInfo');
                        $translatePartialLoader.addPart('instGovernBody');
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
                    },
                    governingBodys:function(){
                        return [
                            {
                                name: null,
                                position: null,
                                mobileNo: null,
                                status: null,
                                institute:null
                            }
                        ];
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
            .state('instituteInfo.adminInfo.edit', {
                parent: 'instituteInfo.adminInfo',
                url: '/{id}/edit',
                data: {
                },
                views: {
                    'instituteView@instituteInfo':{
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/AdminInfo/instAdmInfo-dialog.html',
                        controller: 'InstAdmInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('instGovernBody');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['InstAdmInfoTemp','$stateParams', function(InstAdmInfoTemp,$stateParams) {
                        console.log("check");
                        return InstAdmInfoTemp.get({id : $stateParams.id});
                    }],
                    governingBodys:function(){
                        return [
                            {
                                name: null,
                                position: null,
                                mobileNo: null,
                                status: null
                            }
                        ];
                    }
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
            .state('instituteInfo.adminInfo.delete', {
                parent: 'instituteInfo.adminInfo',
                url: '/{id}/delete',
                data: {
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/AdminInfo/instAdmInfo-delete-dialog.html',
                        controller: 'InstAdmInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstAdmInfo', function(InstAdmInfo) {
                                return InstAdmInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteInfo.adminInfo', null, { reload: true });
                    }, function() {
                        $state.go('instituteInfo.adminInfo');
                    })
                }]
            })
            .state('instituteInfo.adminInfo.approve', {
                parent: 'instituteInfo.approve',
                url: '/admin-info-approve/{admid}',
                data: {
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/AdminInfo/instAdmInfo-approve-dialog.html',
                        controller: 'InstAdmInfoApproveController',
                        size: 'md',
                        resolve: {
                            entity: ['InstAdmInfo', function(InstAdmInfo) {
                                console.log($stateParams);
                                return InstAdmInfo.get({id : $stateParams.admid});

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
            .state('instituteInfo.adminInfo.decline', {
                parent: 'instituteInfo.approve',
                url: '/{id}/admin-info-decline',
                data: {
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/institute-decline.html',
                        controller: 'InstAdmInfoDeclineController',
                        size: 'md',
                        resolve: {
                            entity: ['InstAdmInfo', function(InstAdmInfo) {
                                console.log($stateParams);
                                return InstAdmInfo.get({id : $stateParams.admid});

                            }]
                        }
                    }).result.then(function(result) {
                            console.log(result);
                            $state.go('instituteInfo.approve',{},{reload:true});
                        }, function() {
                            $state.go('instituteInfo.approve',{},{reload:true});
                        })

                }]

            });
    });
