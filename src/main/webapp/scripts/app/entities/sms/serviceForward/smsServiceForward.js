'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('smsServiceForward', {
                parent: 'sms',
                url: '/smsServiceForwards',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.smsServiceForward.home.title'
                },
                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceForward/smsServiceForwards.html',
                        controller: 'SmsServiceForwardController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceForward');
                        $translatePartialLoader.addPart('serviceStatus');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('smsServiceForward.detail', {
                parent: 'smsServiceForward',
                url: '/smsServiceForward/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.smsServiceForward.detail.title'
                },
                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceForward/smsServiceForward-detail.html',
                        controller: 'SmsServiceForwardDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceForward');
                        $translatePartialLoader.addPart('serviceStatus');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SmsServiceForward', function($stateParams, SmsServiceForward) {
                        return SmsServiceForward.get({id : $stateParams.id});
                    }]
                }
            })
            .state('smsServiceForward.new', {
                parent: 'smsServiceForward',
                url: '/new/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceForward/smsServiceForward-dialog.html',
                        controller: 'SmsServiceForwardDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceForward');
                        $translatePartialLoader.addPart('serviceStatus');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SmsServiceComplaint', function($stateParams, SmsServiceComplaint)
                    {
                        return {
                            smsServiceComplaint:SmsServiceComplaint.get({id : $stateParams.id}),
                            cc: null,
                            serviceStatus: null,
                            comments: null,
                            forwardDate: null,
                            activeStatus: null,
                            id: null
                        };
                    }]
                }
            })
            .state('smsServiceForward.edit', {
                parent: 'smsServiceForward',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceForward/smsServiceForward-dialog.html',
                        controller: 'SmsServiceForwardDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceForward');
                        $translatePartialLoader.addPart('serviceStatus');
                        return $translate.refresh();
                    }],
                    entity: ['SmsServiceForward','$stateParams', function(SmsServiceForward, $stateParams) {
                        return SmsServiceForward.get({id : $stateParams.id});
                    }]
                }

            })
            .state('smsServiceForward.delete', {
                parent: 'smsServiceForward',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/sms/serviceForward/smsServiceForward-delete-dialog.html',
                        controller: 'SmsServiceForwardDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SmsServiceForward', function(SmsServiceForward) {
                                return SmsServiceForward.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('smsServiceForward', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
