'use strict';

describe('JpEmploymentHistory Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockJpEmploymentHistory, MockJpEmployee;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockJpEmploymentHistory = jasmine.createSpy('MockJpEmploymentHistory');
        MockJpEmployee = jasmine.createSpy('MockJpEmployee');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'JpEmploymentHistory': MockJpEmploymentHistory,
            'JpEmployee': MockJpEmployee
        };
        createController = function() {
            $injector.get('$controller')("JpEmploymentHistoryDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:jpEmploymentHistoryUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
