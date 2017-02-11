'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            /*.state('instEmplExperience', {
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
            })*/
            .state('employeeInfo.experienceInfo', {
                parent: 'employeeInfo',
                url: '/experience-info',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instEmplExperience.detail.title'
                },
                views: {
                    'employeeView@employeeInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmplExperience/instEmplExperience-detail.html',
                        controller: 'InstEmplExperienceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplExperience');
                        return $translate.refresh();
                    }]
                }
            })
            .state('employeeInfo.experienceInfo.new', {
                parent: 'employeeInfo.experienceInfo',
                url: '/new',
                data: {
                    authorities: [],
                },
                views: {
                    'employeeView@employeeInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmplExperience/instEmplExperience-dialog.html',
                        controller: 'InstEmplExperienceDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplExperience');
                        return $translate.refresh();
                    }],
                   entity: function () {
                       return [{
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
                       }];
                   }
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
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
                }]*/
            })
            .state('employeeInfo.experienceInfo.edit', {
                parent: 'employeeInfo.experienceInfo',
                url: '/edit',
                data: {
                    authorities: [],
                },
                views: {
                    'employeeView@employeeInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmplExperience/instEmplExperience-dialog.html',
                        controller: 'InstEmplExperienceDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplExperience');
                        return $translate.refresh();
                    }],
                    entity: ['InstEmplExperienceCurrent', function(InstEmplExperienceCurrent) {
                        return  InstEmplExperienceCurrent.get();
                    }]
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
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
                }]*/
            })
            .state('employeeInfo.experienceInfo.delete', {
                parent: 'employeeInfo.experienceInfo',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmplExperience/instEmplExperience-delete-dialog.html',
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
