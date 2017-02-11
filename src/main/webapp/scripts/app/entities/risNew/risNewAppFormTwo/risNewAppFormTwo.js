'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('risNew.risNewAppFormTwo', {
                parent: 'risNew',
                url: '/risNewAppFormTwos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.risNewAppFormTwo.home.title'
                },
                views: {
                    'risManagementView@risNew':{
                        templateUrl: 'scripts/app/entities/risNew/risNewAppFormTwo/risNewAppFormTwos.html',
                        controller: 'RisNewAppFormTwoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewAppFormTwo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('risNew.risNewAppFormTwo.detail', {
                parent: 'risNew.risNewAppFormTwo',
                url: '/risNewAppFormTwo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.risNewAppFormTwo.detail.title'
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewAppFormTwo/risNewAppFormTwo-detail.html',
                        controller: 'RisNewAppFormTwoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewAppFormTwo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'RisNewAppFormTwo', function($stateParams, RisNewAppFormTwo) {
                        return RisNewAppFormTwo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('risNew.risNewAppFormTwo.new', {
                parent: 'risNew.risNewAppFormTwo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewAppFormTwo/risNewAppFormTwo-dialog.html',
                        controller: 'RisNewAppFormDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewAppFormTwo');
                        return $translate.refresh();
                    }],
                      entity: function () {
                          return {
                              examName: null,
                              subject: null,
                              educationalInstitute: null,
                              passingYear: null,
                              boardUniversity: null,
                              additionalInformation: null,
                              experience: null,
                              qouta: null,
                              bankDraftNo: null,
                              dateFinDocument: null,
                              bankName: null,
                              branchName: null,
                              departmentalCandidate: null,
                              bankInvoice: null,
                              bankInvoiceContentType: null,
                              signature: null,
                              signatureContentType: null,
                              createdDate: null,
                              updatedDate: null,
                              createdBy: null,
                              updatedBy: null,
                              status: null,
                              id: null
                          };
                      }
                  }
            })
            .state('risNew.risNewAppFormTwo.edit', {
                parent: 'risNew.risNewAppFormTwo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.risNewAppFormTwo.detail.title'
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewAppFormTwo/risNewAppFormTwo-dialog.html',
                        controller: 'RisNewAppFormDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewAppFormTwo');
                        return $translate.refresh();
                    }],
                    entity: ['RisNewAppFormTwo','$stateParams', function(RisNewAppFormTwo, $stateParams) {
                        return RisNewAppFormTwo.get({id : $stateParams.id});
                    }]
                }
            });
    });
