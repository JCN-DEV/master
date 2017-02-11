'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('smsGateway', {
                parent: 'entity',
                url: '/smsGateways',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.smsGateway.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/smsGateway/smsGateways.html',
                        controller: 'SmsGatewayController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsGateway');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('smsGateway.detail', {
                parent: 'entity',
                url: '/smsGateway/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.smsGateway.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/smsGateway/smsGateway-detail.html',
                        controller: 'SmsGatewayDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsGateway');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SmsGateway', function($stateParams, SmsGateway) {
                        return SmsGateway.get({id : $stateParams.id});
                    }]
                }
            })
            .state('smsGateway.new', {
                parent: 'smsGateway',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/smsGateway/smsGateway-dialog.html',
                        controller: 'SmsGatewayDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    userId: null,
                                    phoneNo: null,
                                    msg: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('smsGateway', null, { reload: true });
                    }, function() {
                        $state.go('smsGateway');
                    })
                }]
            })
            .state('smsGateway.edit', {
                parent: 'smsGateway',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/smsGateway/smsGateway-dialog.html',
                        controller: 'SmsGatewayDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SmsGateway', function(SmsGateway) {
                                return SmsGateway.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('smsGateway', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
