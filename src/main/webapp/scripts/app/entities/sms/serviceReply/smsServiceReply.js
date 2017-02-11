'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('smsServiceReply', {
                parent: 'sms',
                url: '/smsServiceReplys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.smsServiceReply.home.title'
                },
                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceReply/smsServiceReplys.html',
                        controller: 'SmsServiceReplyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceReply');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('smsServiceReply.detail', {
                parent: 'smsServiceReply',
                url: '/smsServiceReply/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.smsServiceReply.detail.title'
                },
                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceReply/smsServiceReply-detail.html',
                        controller: 'SmsServiceReplyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceReply');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SmsServiceReply', function($stateParams, SmsServiceReply) {
                        return SmsServiceReply.get({id : $stateParams.id});
                    }]
                }
            })
            .state('smsServiceReply.new', {
                parent: 'smsServiceReply',
                url: '/new/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceReply/smsServiceReply-dialog.html',
                        controller: 'SmsServiceReplyDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceReply');
                        //$translatePartialLoader.addPart('smsServiceComplaint');
                        return $translate.refresh();
                    }],

                    entity: ['$stateParams', 'SmsServiceComplaint', function($stateParams, SmsServiceComplaint)
                    {
                        return {
                            smsServiceComplaint:SmsServiceComplaint.get({id : $stateParams.id}),
                            cc: null,
                            comments: null,
                            replyDate: null,
                            id: null
                        };
                    }]
                }
            })
            .state('smsServiceReply.edit', {
                parent: 'smsServiceReply',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceReply/smsServiceReply-dialog.html',
                        controller: 'SmsServiceReplyDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceReply');
                        return $translate.refresh();
                    }],
                    entity: ['SmsServiceReply','$stateParams', function(SmsServiceReply, $stateParams) {
                        return SmsServiceReply.get({id : $stateParams.id});
                    }]
                }
            })
            .state('smsServiceReply.delete', {
                parent: 'smsServiceReply',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/sms/serviceReply/smsServiceReply-delete-dialog.html',
                        controller: 'SmsServiceReplyDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SmsServiceReply', function(SmsServiceReply) {
                                return SmsServiceReply.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('smsServiceReply', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
