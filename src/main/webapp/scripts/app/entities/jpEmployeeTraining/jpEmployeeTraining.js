'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('jpEmployeeTraining', {
                parent: 'entity',
                url: '/jpEmployeeTrainings',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpEmployeeTraining.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpEmployeeTraining/jpEmployeeTrainings.html',
                        controller: 'JpEmployeeTrainingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpEmployeeTraining');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('jpEmployeeTraining.detail', {
                parent: 'entity',
                url: '/jpEmployeeTraining/{id}',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpEmployeeTraining.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpEmployeeTraining/jpEmployeeTraining-detail.html',
                        controller: 'JpEmployeeTrainingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpEmployeeTraining');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpEmployeeTraining', function($stateParams, JpEmployeeTraining) {
                        return JpEmployeeTraining.get({id : $stateParams.id});
                    }]
                }
            })
            .state('jpEmployeeTraining.new', {
                parent: 'jpEmployeeTraining',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpEmployeeTraining.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpEmployeeTraining/jpEmployeeTraining-form.html',
                        controller: 'JpEmployeeTrainingFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpEmployeeTraining');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpEmployeeTraining', function($stateParams, JpEmployeeTraining) {
                        //return JpEmployeeTraining.get({id : $stateParams.id});
                    }]
                }
            })
            .state('jpEmployeeTraining.edit', {
                parent: 'jpEmployeeTraining',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpEmployeeTraining.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpEmployeeTraining/jpEmployeeTraining-form.html',
                        controller: 'JpEmployeeTrainingFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpEmployeeTraining');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpEmployeeTraining', function($stateParams, JpEmployeeTraining) {
                        return JpEmployeeTraining.get({id : $stateParams.id});
                    }]
                }
            });
    });
