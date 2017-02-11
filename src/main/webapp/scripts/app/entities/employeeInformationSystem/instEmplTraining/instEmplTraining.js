'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            /*.state('employeeInfo.trainingInfo', {
                parent: 'employeeInfo',
                url: '/trainings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmplTraining.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmplTraining/instEmplTrainings.html',
                        controller: 'InstEmplTrainingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplTraining');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })*/
            .state('employeeInfo.trainingInfo', {
                parent: 'employeeInfo',
                url: '/training-info',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instEmplTraining.detail.title'
                },
                views: {
                    'employeeView@employeeInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmplTraining/instEmplTraining-detail.html',
                        controller: 'InstEmplTrainingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplTraining');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('employeeInfo.trainingInfo.new', {
                parent: 'employeeInfo.trainingInfo',
                url: '/new',
                data: {
                    authorities: []
                },
                params: {
                    instEmplTrainings:null
                },
                views: {
                    'employeeView@employeeInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmplTraining/instEmplTraining-dialog.html',
                        controller: 'InstEmplTrainingDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplTraining');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return [{
                            name: null,
                            subjectsCoverd: null,
                            location: null,
                            startedDate: null,
                            endedDate: null,
                            result: null,
                            id: null
                        }];
                    }
                }
               /* onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplTraining/instEmplTraining-dialog.html',
                        controller: 'InstEmplTrainingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    subjectsCoverd: null,
                                    location: null,
                                    startedDate: null,
                                    endedDate: null,
                                    result: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplTraining', null, { reload: true });
                    }, function() {
                        $state.go('instEmplTraining');
                    })
                }]*/
            })
            .state('employeeInfo.trainingInfo.edit', {
                parent: 'employeeInfo.trainingInfo',
                url: '/edit',
                data: {
                    authorities: [],
                },
                params: {
                    instEmplTrainings:null
                },
                views: {
                    'employeeView@employeeInfo': {
                         templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmplTraining/instEmplTraining-dialog.html',
                         controller: 'InstEmplTrainingDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplTraining');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity:['InstEmplTrainingCurrent',function(InstEmplTrainingCurrent){
                        //return InstEmplTrainingCurrent.get();
                        return null;
                    }]
                }

                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplTraining/instEmplTraining-dialog.html',
                        controller: 'InstEmplTrainingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstEmplTraining', function(InstEmplTraining) {
                                return InstEmplTraining.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplTraining', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            })
            .state('employeeInfo.trainingInfo.delete', {
                parent: 'employeeInfo.trainingInfo',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmplTraining/instEmplTraining-delete-dialog.html',
                        controller: 'InstEmplTrainingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstEmplTraining', function(InstEmplTraining) {
                                return InstEmplTraining.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplTraining', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('employeeInfo.trainingInfo.view', {
                parent: 'employeeInfo.trainingInfo',
                url: '/view',
                data: {
                    authorities: [],
                },
                views: {
                    'employeeView@employeeInfo': {
                         templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmplTraining/instEmplTraining-detail.html',
                         controller: 'InstEmplTrainingDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplTraining');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['instEmplTraining','$stateParams', function(instEmplTraining,$stateParams) {
                       return instEmplTraining.get({id : $stateParams.id});
                    }]
                }

                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplTraining/instEmplTraining-dialog.html',
                        controller: 'InstEmplTrainingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstEmplTraining', function(InstEmplTraining) {
                                return InstEmplTraining.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplTraining', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            })
    });
