'use strict';

angular.module('stepApp')
    .factory('DlBookIssue', function ($resource, DateUtils) {
        return $resource('api/dlBookIssues/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.returnDate = DateUtils.convertLocaleDateFromServer(data.returnDate);
                    data.createdDate = DateUtils.convertLocaleDateFromServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateFromServer(data.updatedDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.returnDate = DateUtils.convertLocaleDateToServer(data.returnDate);
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateToServer(data.updatedDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.returnDate = DateUtils.convertLocaleDateToServer(data.returnDate);
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateToServer(data.updatedDate);
                    return angular.toJson(data);
                }
            }
        });



    })

    .factory('IssueInfoByid', function ($resource) {
              return $resource('api/dlBookIssues/findIssueInfoByid/:id', {}, {
                  'get': { method: 'GET', isArray: false}
              });
    })
    .factory('FindDlBookIssueByInstId', function ($resource) {
        return $resource('api/dlBookIssues/FindDlBookIssueByInstId', {}, {
            'query': { method: 'GET', isArray: true}
        })
    })

    .factory('FindAllByUserRequisition', function ($resource) {
          return $resource('api/dlBookIssues/byUser', {}, {
              'query': {method: 'GET', isArray: true}
          });
    })

    .factory('DlIssueByStudentId', function ($resource) {
          return $resource('api/dlBookIssues/findIssueInfoByStuid/:id', {}, {
              'get': { method: 'GET', isArray: true}
         });
    })


    .factory('DlIssueByIssueId', function ($resource) {
          return $resource('api/dlBookIssues/findIssueInfoByIssueId/:id', {}, {
              'get': { method: 'GET', isArray: false}
         });
    })

    .factory('findAllBookIssue', function ($resource) {
          return $resource('api/dlBookIssues/findAllBookIssue/:sisId/:BookInfoId/:BookEdiId/', {}, {
              'get': { method: 'GET', isArray: false}
         });
    })
    .factory('findBookIssueForStudentRole', function ($resource) {
          return $resource('api/dlBookIssue/findBookIssueForStudentRole/:bookId/:BookEditionId', {}, {
              'get': { method: 'GET', isArray: false}
         });
    });
