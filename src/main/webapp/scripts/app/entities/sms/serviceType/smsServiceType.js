'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('smsServiceType', {
                parent: 'sms',
                url: '/smsServiceTypes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.smsServiceType.home.title'
                },
                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceType/smsServiceTypes.html',
                        controller: 'SmsServiceTypeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('smsServiceType.detail', {
                parent: 'smsServiceType',
                url: '/smsServiceType/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.smsServiceType.detail.title'
                },
                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceType/smsServiceType-detail.html',
                        controller: 'SmsServiceTypeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SmsServiceType', function($stateParams, SmsServiceType) {
                        return SmsServiceType.get({id : $stateParams.id});
                    }]
                }
            })
            .state('smsServiceType.new', {
                parent: 'smsServiceType',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceType/smsServiceType-dialog.html',
                        controller: 'SmsServiceTypeDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            serviceName: null,
                            description: null,
                            activeStatus: null,
                            id: null
                        };
                    }
                }

            })
            .state('smsServiceType.edit', {
                parent: 'smsServiceType',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceType/smsServiceType-dialog.html',
                        controller: 'SmsServiceTypeDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['SmsServiceType','$stateParams', function(SmsServiceType,$stateParams) {
                        return SmsServiceType.get({id : $stateParams.id});
                    }]
                }
            })
            .state('smsServiceType.delete', {
                parent: 'smsServiceType',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/sms/serviceType/smsServiceType-delete-dialog.html',
                        controller: 'SmsServiceTypeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SmsServiceType', function(SmsServiceType) {
                                return SmsServiceType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('smsServiceType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
