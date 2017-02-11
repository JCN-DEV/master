'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('payScale', {
                parent: 'entity',
                url: '/payScales',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.payScale.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/payScale/payScales.html',
                        controller: 'PayScaleController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('payScale.detail', {
                parent: 'entity',
                url: '/payScale/{id}',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.payScale.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/payScale/payScale-detail.html',
                        controller: 'PayScaleDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('payScale');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PayScale', function($stateParams, PayScale) {
                        return PayScale.get({id : $stateParams.id});
                    }]
                }
            })
            .state('payScale.new', {
                parent: 'payScale',
                url: '/new',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/payScale/payScale-form.html',
                        controller: 'PayScaleFormController',
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('country');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', function($stateParams) {
                        return {
                            code: null,
                            payScaleClass: null,
                            grade: null,
                            gradeName: null,
                            basicAmount: null,
                            houseAllowance: null,
                            medicalAllowance: null,
                            welfareAmount: null,
                            retirementAmount: null,
                            date: null,
                            id: null
                        };
                    }]
                }
            })
//            .state('payScale.new', {
//                parent: 'payScale',
//                url: '/new',
//                data: {
//                    authorities: ['ROLE_USER'],
//                },
//                onEnter: ['$stateParams', '$state', function($stateParams, $state, $modal) {
//                    $modal.open({
//                        templateUrl: 'scripts/app/entities/payScale/payScale-form.html',
//                        controller: 'PayScaleFormController',
//                        size: 'lg',
//                        resolve: {
//                            entity: function () {
//                                return {
//                                    code: null,
//                                    payScaleClass: null,
//                                    grade: null,
//                                    gradeName: null,
//                                    basicAmount: null,
//                                    houseAllowance: null,
//                                    medicalAllowance: null,
//                                    welfareAmount: null,
//                                    retirementAmount: null,
//                                    date: null,
//                                    id: null
//                                };
//                            }
//                        }
//                    }).result.then(function(result) {
//                        $state.go('payScale', null, { reload: true });
//                    }, function() {
//                        $state.go('payScale');
//                    })
//                }]
//            })
            .state('payScale.edit', {
                parent: 'payScale',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/payScale/payScale-form.html',
                        controller: 'PayScaleFormController',
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('country');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PayScale', function($stateParams, PayScale) {
                        return PayScale.get({id : $stateParams.id});
                    }]
                }
            })
//            .state('payScale.edit', {
//                parent: 'payScale',
//                url: '/{id}/edit',
//                data: {
//                    authorities: ['ROLE_USER'],
//                },
//                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
//                    $modal.open({
//                        templateUrl: 'scripts/app/entities/payScale/payScale-dialog.html',
//                        controller: 'PayScaleDialogController',
//                        size: 'lg',
//                        resolve: {
//                            entity: ['PayScale', function(PayScale) {
//                                return PayScale.get({id : $stateParams.id});
//                            }]
//                        }
//                    }).result.then(function(result) {
//                        $state.go('payScale', null, { reload: true });
//                    }, function() {
//                        $state.go('^');
//                    })
//                }]
//            })
            .state('payScale.delete', {
                parent: 'payScale',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payScale/payScale-delete-dialog.html',
                        controller: 'PayScaleDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PayScale', function(PayScale) {
                                return PayScale.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('payScale', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
