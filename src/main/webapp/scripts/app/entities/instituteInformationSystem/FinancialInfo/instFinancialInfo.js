'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            /*.state('instFinancialInfo', {
                parent: 'entity',
                url: '/instFinancialInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instFinancialInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instFinancialInfo/instFinancialInfos.html',
                        controller: 'InstFinancialInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instFinancialInfo');
                        $translatePartialLoader.addPart('accountType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })*/
            .state('instituteInfo.financialInfo', {
                parent: 'instituteInfo',
                url: '/Financial-Info',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instFinancialInfo.detail.title'
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/FinancialInfo/instFinancialInfo-detail.html',
                        controller: 'InstFinancialInfoDetailController'

                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instFinancialInfo');
                        $translatePartialLoader.addPart('accountType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', function($stateParams) {
                        return null;
                    }]
                }
            })
            .state('instituteInfo.financialInfo.new', {
                parent: 'instituteInfo.financialInfo',
                url: '/new',
                data: {
                    authorities: [],
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/FinancialInfo/instFinancialInfo-dialog.html',
                        controller: 'InstFinancialInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instFinancialInfo');
                        $translatePartialLoader.addPart('accountType');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return [{
                            bankName: null,
                            branchName: null,
                            accountType: null,
                            accountNo: null,
                            issueDate: null,
                            expireDate: null,
                            amount: null,
                            status: null
                        }];
                    }
                }
               /* onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instFinancialInfo/instFinancialInfo-dialog.html',
                        controller: 'InstFinancialInfoDialogController',
                        size: 'lg',
                        resolve: {
                            translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart('instFinancialInfo');
                                $translatePartialLoader.addPart('accountType');
                                return $translate.refresh();
                            }],
                            entity: function () {
                                return {
                                    bankName: null,
                                    branchName: null,
                                    accountType: null,
                                    accountNo: null,
                                    issueDate: null,
                                    expireDate: null,
                                    amount: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instGenInfo.instFinancialInfo', null, { reload: true });
                    }, function() {
                        $state.go('instGenInfo.instFinancialInfo');
                    })
                }]*/
            })
            .state('instituteInfo.financialInfo.edit', {
                parent: 'instituteInfo.financialInfo',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/FinancialInfo/instFinancialInfo-dialog.html',
                        controller: 'InstFinancialInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instFinancialInfo');
                        $translatePartialLoader.addPart('accountType');
                        return $translate.refresh();
                    }],
                    entity: ['InstFinancialInfo','$stateParams', function(InstFinancialInfo,$stateParams) {
                        //return InstFinancialInfo.get({id : $stateParams.id});
                        return null;
                    }]
                }
                /*
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/FinancialInfo/instFinancialInfo-dialog.html',
                        controller: 'InstFinancialInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstFinancialInfo', function(InstFinancialInfo) {
                                return InstFinancialInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteInfo.financialInfo', null, { reload: true });
                    }, function() {
                        $state.go('instituteInfo.financialInfo');
                    })
                }]*/
            })
            .state('instituteInfo.financialInfo.delete', {
                parent: 'instituteInfo.financialInfo',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/FinancialInfo/instFinancialInfo-delete-dialog.html',
                        controller: 'InstFinancialInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstFinancialInfo', function(InstFinancialInfo) {
                                return InstFinancialInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                            $state.go('instituteInfo.financialInfo', null, { reload: true });
                        }, function() {
                            $state.go('instituteInfo.financialInfo');
                        })
                }]

            })
            .state('instituteInfo.financialInfo.approve', {
                parent: 'instituteInfo.approve',
                url: '/financial-info-approve/{instid}',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/FinancialInfo/instFinancialInfo-approve-dialog.html',
                        controller: 'InstFinancialInfoApproveController',
                        size: 'md',
                        resolve: {
                            entity: ['InstFinancialInfo', function(InstFinancialInfo) {
                                return InstFinancialInfo.get({id : $stateParams.instid});
                                //  return InstGenInfo.get({id : $stateParams.id});
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
            .state('instituteInfo.financialInfo.decline', {
                parent: 'instituteInfo.approve',
                url: '/financial-info-decline/{instid}',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/institute-decline.html',
                        controller: 'InstFinancialInfoDeclineController',
                        size: 'md'
                    }).result.then(function(result) {
                            console.log(result);
                            $state.go('instituteInfo.approve',{},{reload:true});
                        }, function() {
                            $state.go('instituteInfo.approve',{},{reload:true});
                        })

                }]

            });
    });
