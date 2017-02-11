'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instEmplExperience', {
                parent: 'entity',
                url: '/instEmplExperiences',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmplExperience.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmplExperience/instEmplExperiences.html',
                        controller: 'InstEmplExperienceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplExperience');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instEmplExperience.detail', {
                parent: 'entity',
                url: '/instEmplExperience/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmplExperience.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmplExperience/instEmplExperience-detail.html',
                        controller: 'InstEmplExperienceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplExperience');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstEmplExperience', function($stateParams, InstEmplExperience) {
                        return InstEmplExperience.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instEmplExperience.new', {
                parent: 'instEmplExperience',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplExperience/instEmplExperience-dialog.html',
                        controller: 'InstEmplExperienceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    instituteName: null,
                                    indexNo: null,
                                    address: null,
                                    designation: null,
                                    subject: null,
                                    salaryCode: null,
                                    joiningDate: null,
                                    mpoEnlistingDate: null,
                                    resignDate: null,
                                    dateOfPaymentReceived: null,
                                    startDate: null,
                                    endDate: null,
                                    vacationFrom: null,
                                    vacationTo: null,
                                    totalExpFrom: null,
                                    totalExpTo: null,
                                    current: null,
                                    attachment: null,
                                    attachmentContentType: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplExperience', null, { reload: true });
                    }, function() {
                        $state.go('instEmplExperience');
                    })
                }]
            })
            .state('instEmplExperience.edit', {
                parent: 'instEmplExperience',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplExperience/instEmplExperience-dialog.html',
                        controller: 'InstEmplExperienceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstEmplExperience', function(InstEmplExperience) {
                                return InstEmplExperience.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplExperience', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instEmplExperience.delete', {
                parent: 'instEmplExperience',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplExperience/instEmplExperience-delete-dialog.html',
                        controller: 'InstEmplExperienceDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstEmplExperience', function(InstEmplExperience) {
                                return InstEmplExperience.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplExperience', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
