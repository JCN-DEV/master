'use strict';

angular.module('stepApp')
    .factory('DlExistingBookIssue', function ($resource, DateUtils) {
        return $resource('api/dlExistingBookIssues/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.issueDate = DateUtils.convertLocaleDateFromServer(data.issueDate);
                    data.returnDate = DateUtils.convertLocaleDateFromServer(data.returnDate);
                    data.actReturnDate = DateUtils.convertLocaleDateFromServer(data.actReturnDate);
                    data.createdDate = DateUtils.convertLocaleDateFromServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateFromServer(data.updatedDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.issueDate = DateUtils.convertLocaleDateToServer(data.issueDate);
                    data.returnDate = DateUtils.convertLocaleDateToServer(data.returnDate);
                    data.actReturnDate = DateUtils.convertLocaleDateToServer(data.actReturnDate);
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateToServer(data.updatedDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.issueDate = DateUtils.convertLocaleDateToServer(data.issueDate);
                    data.returnDate = DateUtils.convertLocaleDateToServer(data.returnDate);
                    data.actReturnDate = DateUtils.convertLocaleDateToServer(data.actReturnDate);
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateToServer(data.updatedDate);
                    return angular.toJson(data);
                }
            }
        });
    });
