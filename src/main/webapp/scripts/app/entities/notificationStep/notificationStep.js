'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('notificationStep', {
                parent: 'entity',
                url: '/notificationSteps',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.notificationStep.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/notificationStep/notificationSteps.html',
                        controller: 'NotificationStepController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('notificationStep');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('notificationStep.detail', {
                parent: 'entity',
                url: '/notificationStep/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.notificationStep.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/notificationStep/notificationStep-detail.html',
                        controller: 'NotificationStepDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('notificationStep');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'NotificationStep', function($stateParams, NotificationStep) {
                        return NotificationStep.get({id : $stateParams.id});
                    }]
                }
            })
            .state('notificationStep.new', {
                parent: 'notificationStep',
                url: '/new',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/notificationStep/notificationStep-dialog.html',
                        controller: 'NotificationStepDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    userId: null,
                                    notifyDate: null,
                                    urls: null,
                                    status: null,
                                    createBy: null,
                                    createDate: null,
                                    updateBy: null,
                                    updateDate: null,
                                    remarks: null,
                                    notification: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('notificationStep', null, { reload: true });
                    }, function() {
                        $state.go('notificationStep');
                    })
                }]
            })
            .state('notificationStep.edit', {
                parent: 'notificationStep',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/notificationStep/notificationStep-dialog.html',
                        controller: 'NotificationStepDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['NotificationStep', function(NotificationStep) {
                                return NotificationStep.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('notificationStep', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
