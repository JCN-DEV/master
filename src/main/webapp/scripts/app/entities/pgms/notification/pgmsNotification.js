'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pgmsNotification', {
                parent: 'pgms',
                url: '/pgmsNotifications',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsNotification.home.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/notification/pgmsNotifications.html',
                        controller: 'PgmsNotificationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsNotification');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pgmsNotificationSentList', {
                parent: 'pgms',
                url: '/pgmsNotificationSendingList',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsNotification.home.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/notification/pgmsNotificationSentList.html',
                        controller: 'PgmsNotificationSentListRequestController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsNotification');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('hrmHome');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pgmsNotification.detail', {
                parent: 'pgms',
                url: '/pgmsNotification/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsNotification.detail.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/notification/pgmsNotification-detail.html',
                        controller: 'PgmsNotificationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsNotification');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PgmsNotification', function($stateParams, PgmsNotification) {
                        return PgmsNotification.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pgmsNotification.new', {
                parent: 'pgmsNotification',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                      templateUrl: 'scripts/app/entities/pgms/notification/pgmsNotification-dialog.html',
                      controller: 'PgmsNotificationDialogController'
                     }
                },
                resolve: {
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsNotification');
                        return $translate.refresh();
                      }],
                      entity: function () {
                              return {
                                    empId: null,
                                    empName: null,
                                    empDesignation: null,
                                    dateOfBirth: null,
                                    joiningDate: null,
                                    retiremnntDate: null,
                                    workDuration: null,
                                    contactNumber: null,
                                    message: null,
                                    notificationStatus: null,
                                    activeStatus: null,
                                    createDate: null,
                                    createBy: null,
                                    updateDate: null,
                                    updateBy: null,
                                    id: null
                              }
                       }
                }
            })
            .state('pgmsNotification.edit', {
                parent: 'pgmsNotification',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                          templateUrl: 'scripts/app/entities/pgms/notification/pgmsNotification-dialog.html',
                          controller: 'PgmsNotificationDialogController'
                     }
                },
                resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsNotification');
                        return $translate.refresh();
                     }],
                     entity: ['$stateParams','PgmsNotification', function($stateParams,PgmsNotification) {
                               return PgmsNotification.get({id : $stateParams.id});
                     }]
                }
            });
    });
