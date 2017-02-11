'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('jpEmployee', {
                parent: 'entity',
                url: '/jpEmployees',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpEmployee.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpEmployee/jpEmployees.html',
                        controller: 'JpEmployeeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpEmployee');
                        $translatePartialLoader.addPart('employeeGender');
                        $translatePartialLoader.addPart('maritialStatus');
                        $translatePartialLoader.addPart('nationality');
                        $translatePartialLoader.addPart('employeeAvailability');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('jpEmployee.detail', {
                parent: 'entity',
                url: '/jpEmployee/{id}',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpEmployee.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpEmployee/jpEmployee-detail.html',
                        controller: 'JpEmployeeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpEmployee');
                        $translatePartialLoader.addPart('employeeGender');
                        $translatePartialLoader.addPart('maritialStatus');
                        $translatePartialLoader.addPart('nationality');
                        $translatePartialLoader.addPart('employeeAvailability');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpEmployee', function($stateParams, JpEmployee) {
                        return JpEmployee.get({id : $stateParams.id});
                    }]
                }
            })
            .state('jpEmployee.new', {
                parent: 'jpEmployee',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpEmployee.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpEmployee/jpEmployee-form.html',
                        controller: 'JpEmployeeFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpEmployee');
                        $translatePartialLoader.addPart('employeeGender');
                        $translatePartialLoader.addPart('maritialStatus');
                        $translatePartialLoader.addPart('nationality');
                        $translatePartialLoader.addPart('employeeAvailability');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpEmployee', function($stateParams, JpEmployee) {

                    }]
                }
            })
            .state('jpEmployee.edit', {
                parent: 'jpEmployee',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpEmployee.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpEmployee/jpEmployee-form.html',
                        controller: 'JpEmployeeFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpEmployee');
                        $translatePartialLoader.addPart('employeeGender');
                        $translatePartialLoader.addPart('maritialStatus');
                        $translatePartialLoader.addPart('nationality');
                        $translatePartialLoader.addPart('employeeAvailability');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpEmployee', function($stateParams, JpEmployee) {
                        return JpEmployee.get({id : $stateParams.id});
                    }]
                }
            });
    });
