'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('withedSalary', {
                parent: 'entity',
                url: '/withedSalary',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.deo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/withheldSalary/withhedSalary.html',
                        controller: 'withedSalaryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('deo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            }).state('stopStartSalary', {
                parent: 'entity',
                url: '/salary-control',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.deo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/withheldSalary/emp_salary_manage.html',
                        //EmployeeSalaryController located within 'scripts/app/entities/withheldSalary/withheldSalary.controller.js'
                        controller: 'EmployeeSalaryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('deo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })

    });
