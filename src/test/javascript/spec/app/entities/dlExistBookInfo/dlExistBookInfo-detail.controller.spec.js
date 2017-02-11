'use strict';

describe('DlExistBookInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDlExistBookInfo, MockDlBookType, MockDlContentSetup;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDlExistBookInfo = jasmine.createSpy('MockDlExistBookInfo');
        MockDlBookType = jasmine.createSpy('MockDlBookType');
        MockDlContentSetup = jasmine.createSpy('MockDlContentSetup');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'DlExistBookInfo': MockDlExistBookInfo,
            'DlBookType': MockDlBookType,
            'DlContentSetup': MockDlContentSetup
        };
        createController = function() {
            $injector.get('$controller')("DlExistBookInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:dlExistBookInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
