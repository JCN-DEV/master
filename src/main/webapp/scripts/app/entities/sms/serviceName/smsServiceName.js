'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('smsServiceName', {
                parent: 'sms',
                url: '/smsServiceNames',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.smsServiceName.home.title'
                },
                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceName/smsServiceNames.html',
                        controller: 'SmsServiceNameController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceName');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('smsServiceName.detail', {
                parent: 'smsServiceName',
                url: '/smsServiceName/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.smsServiceName.detail.title'
                },
                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceName/smsServiceName-detail.html',
                        controller: 'SmsServiceNameDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceName');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SmsServiceName', function($stateParams, SmsServiceName) {
                        return SmsServiceName.get({id : $stateParams.id});
                    }]
                }
            })
            .state('smsServiceName.new', {
                parent: 'smsServiceName',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceName/smsServiceName-dialog.html',
                        controller: 'SmsServiceNameDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceName');
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
            .state('smsServiceName.edit', {
                parent: 'smsServiceName',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceName/smsServiceName-dialog.html',
                        controller: 'SmsServiceNameDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceName');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['SmsServiceName','$stateParams', function(SmsServiceName,$stateParams) {
                        return SmsServiceName.get({id : $stateParams.id});
                    }]
                }

            })
            .state('smsServiceName.delete', {
                parent: 'smsServiceName',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/sms/serviceName/smsServiceName-delete-dialog.html',
                        controller: 'SmsServiceNameDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SmsServiceName', function(SmsServiceName) {
                                return SmsServiceName.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                            $state.go('smsServiceName', null, { reload: true });
                        }, function() {
                            $state.go('^');
                        })
                }]
            });
    });
