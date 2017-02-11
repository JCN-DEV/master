'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider

            .state('employeeInfo.spouseInfo', {
                parent: 'employeeInfo',
                url: '/spouse-info',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instEmpSpouseInfo.detail.title'
                },
                views: {
                    'employeeView@employeeInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmpSpouseInfo/instEmpSpouseInfo-detail.html',
                        controller: 'InstEmpSpouseInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmpSpouseInfo');
                        $translatePartialLoader.addPart('relationType');
                        return $translate.refresh();
                    }]
                }
            })
            .state('employeeInfo.spouseInfo.new', {
                parent: 'employeeInfo.spouseInfo',
                url: '/new',
                data: {
                    authorities: [],
                },
                views: {
                    'employeeView@employeeInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmpSpouseInfo/instEmpSpouseInfo-dialog.html',
                        controller: 'InstEmpSpouseInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmpSpouseInfo');
                        $translatePartialLoader.addPart('relationType');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            name: null,
                            dob: null,
                            isNominee: false,
                            gender: 'male',
                            relation: null,
                            nomineePercentage: null,
                            occupation: null,
                            tin: null,
                            nid: null,
                            designation: null,
                            govJobId: null,
                            mobile: null,
                            officeContact: null,
                            id: null
                        };
                    }

                }

            })
            .state('employeeInfo.spouseInfo.edit', {
                parent: 'employeeInfo.spouseInfo',
                url: '/edit',
                data: {
                    authorities: [],
                },
                views: {
                    'employeeView@employeeInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmpSpouseInfo/instEmpSpouseInfo-dialog.html',
                        controller: 'InstEmpSpouseInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmpSpouseInfo');
                        $translatePartialLoader.addPart('relationType');
                        return $translate.refresh();
                    }],
                    entity:['InstEmpSpouseInfoForCurrent',function(InstEmpSpouseInfoForCurrent){
                        return InstEmpSpouseInfoForCurrent.get();
                    }]
                }

            })
            .state('employeeInfo.spouseInfo.delete', {
                parent: 'employeeInfo.spouseInfo',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmpSpouseInfo/instEmpSpouseInfo-delete-dialog.html',
                        controller: 'InstEmpSpouseInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstEmpSpouseInfo', function(InstEmpSpouseInfo) {
                                return InstEmpSpouseInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmpSpouseInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
