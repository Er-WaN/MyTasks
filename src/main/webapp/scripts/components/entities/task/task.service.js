'use strict';

angular.module('myTasksApp')
    .factory('Task', function ($resource, DateUtils) {
        return $resource('api/tasks/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.creationDate = DateUtils.convertLocaleDateFromServer(data.creationDate);
                    data.lastEditDate = DateUtils.convertLocaleDateFromServer(data.lastEditDate);
                    data.finishDate = DateUtils.convertLocaleDateFromServer(data.finishDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.creationDate = DateUtils.convertLocaleDateToServer(data.creationDate);
                    data.lastEditDate = DateUtils.convertLocaleDateToServer(data.lastEditDate);
                    data.finishDate = DateUtils.convertLocaleDateToServer(data.finishDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.creationDate = DateUtils.convertLocaleDateToServer(data.creationDate);
                    data.lastEditDate = DateUtils.convertLocaleDateToServer(data.lastEditDate);
                    data.finishDate = DateUtils.convertLocaleDateToServer(data.finishDate);
                    return angular.toJson(data);
                }
            }
        });
    });
