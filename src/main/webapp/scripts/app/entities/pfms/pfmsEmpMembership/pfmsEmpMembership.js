'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pfmsEmpMembership', {
                parent: 'pfms',
                url: '/pfmsEmpMemberships',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsEmpMembership.home.title',
                    displayName: 'Employee Membership'

                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsEmpMembership/pfmsEmpMemberships.html',
                        controller: 'PfmsEmpMembershipController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsEmpMembership');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pfmsEmpMembership.detail', {
                parent: 'pfms',
                url: '/pfmsEmpMembership/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsEmpMembership.detail.title',
                    displayName: 'Employee Membership Details'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsEmpMembership/pfmsEmpMembership-detail.html',
                        controller: 'PfmsEmpMembershipDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsEmpMembership');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PfmsEmpMembership', function($stateParams, PfmsEmpMembership) {
                        return PfmsEmpMembership.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pfmsEmpMembership.new', {
                parent: 'pfmsEmpMembership',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                    displayName: 'Add a Employee Membership'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsEmpMembership/pfmsEmpMembership-dialog.html',
                        controller: 'PfmsEmpMembershipDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsEmpMembership');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            initOwnContribute: null,
                            initOwnContributeInt: null,
                            curOwnContribute: null,
                            curOwnContributeInt: null,
                            curOwnContributeTot: null,
                            percentOfDeduct: null,
                            activeStatus: true
                        };
                    }
                }
            })
            .state('pfmsEmpMembership.edit', {
                parent: 'pfmsEmpMembership',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                    displayName: 'Edit Employee Membership'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsEmpMembership/pfmsEmpMembership-dialog.html',
                        controller: 'PfmsEmpMembershipDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsEmpMembership');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PfmsEmpMembership', function($stateParams, PfmsEmpMembership) {
                        //return PfmsEmpMembership.get({id : $stateParams.id});
                    }]
                }
            });
    });
