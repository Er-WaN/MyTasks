'use strict';

angular.module('myTasksApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('task', {
                parent: 'entity',
                url: '/tasks',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'myTasksApp.task.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/task/tasks.html',
                        controller: 'TaskController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('task');
                        $translatePartialLoader.addPart('taskPriority');
                        $translatePartialLoader.addPart('taskComplexity');
                        $translatePartialLoader.addPart('taskState');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('task.detail', {
                parent: 'entity',
                url: '/task/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'myTasksApp.task.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/task/task-detail.html',
                        controller: 'TaskDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('task');
                        $translatePartialLoader.addPart('taskPriority');
                        $translatePartialLoader.addPart('taskComplexity');
                        $translatePartialLoader.addPart('taskState');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Task', function($stateParams, Task) {
                        return Task.get({id : $stateParams.id});
                    }]
                }
            })
            .state('task.new', {
                parent: 'task',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/task/task-dialog.html',
                        controller: 'TaskDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    creationDate: null,
                                    lastEditDate: null,
                                    finishDate: null,
                                    priority: null,
                                    complexity: null,
                                    state: null,
                                    estimatedTime: null,
                                    description: null,
                                    comment: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('task', null, { reload: true });
                    }, function() {
                        $state.go('task');
                    })
                }]
            })
            .state('task.edit', {
                parent: 'task',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/task/task-dialog.html',
                        controller: 'TaskDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Task', function(Task) {
                                return Task.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('task', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('task.delete', {
                parent: 'task',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/task/task-delete-dialog.html',
                        controller: 'TaskDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Task', function(Task) {
                                return Task.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('task', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
